package com.example.kespy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TextView tvPlayerTurn, tvInstruction, tvSecretWord;
    CardView cardReveal;
    Button btnNextPlayer, btnGoToVote;

    int totalPlayers, totalSpies, categoryId;
    String secretWord = "";
    ArrayList<String> playerRoles = new ArrayList<>();
    ArrayList<String> playerNames = new ArrayList<>();
    int currentPlayerIndex = 0;
    boolean isRevealed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvPlayerTurn = findViewById(R.id.tvPlayerTurn);
        tvInstruction = findViewById(R.id.tvInstruction);
        tvSecretWord = findViewById(R.id.tvSecretWord);
        cardReveal = findViewById(R.id.cardReveal);
        btnNextPlayer = findViewById(R.id.btnNextPlayer);
        btnGoToVote = findViewById(R.id.btnGoToVote);

        totalPlayers = getIntent().getIntExtra("PLAYERS", 3);
        totalSpies = getIntent().getIntExtra("SPIES", 1);
        categoryId = getIntent().getIntExtra("CATEGORY_ID", 1);
        playerNames = getIntent().getStringArrayListExtra("PLAYER_NAMES");

        fetchRandomWord();
        assignRoles();
        updateUI();

        cardReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRevealed) {
                    tvSecretWord.setText(playerRoles.get(currentPlayerIndex));
                    tvInstruction.setText("Card e abar tap kore hide koro");
                    isRevealed = true;
                    btnNextPlayer.setVisibility(View.GONE);
                } else {
                    tvSecretWord.setText("Role Hidden");
                    tvInstruction.setText("Porer jon ke phone pass koro");
                    cardReveal.setClickable(false);
                    btnNextPlayer.setVisibility(View.VISIBLE);
                }
            }
        });

        btnNextPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayerIndex++;
                if (currentPlayerIndex < totalPlayers) {
                    updateUI();
                } else {
                    tvPlayerTurn.setText("Game Started!");
                    tvInstruction.setText("Alochona kore Spy ke khuje ber koro!");
                    cardReveal.setVisibility(View.GONE);
                    btnNextPlayer.setVisibility(View.GONE);
                    btnGoToVote.setVisibility(View.VISIBLE);

                    Random random = new Random();
                    int randomIndex = random.nextInt(playerNames.size());
                    String startingPlayer = playerNames.get(randomIndex);

                    new AlertDialog.Builder(GameActivity.this)
                            .setTitle("খেলা শুরু!")
                            .setMessage("প্রথম Hint দিবে: " + startingPlayer + "\n\n" + startingPlayer + ", তুমি Hint দিয়ে খেলা শুরু করো!")
                            .setPositiveButton("ঠিক আছে", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .setCancelable(false)
                            .show();
                }
            }
        });

        btnGoToVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> actualSpyNamesList = new ArrayList<>();
                for (int i = 0; i < playerRoles.size(); i++) {
                    if (playerRoles.get(i).contains("Spy") || playerRoles.get(i).contains("SPY")) {
                        actualSpyNamesList.add(playerNames.get(i));
                    }
                }
                String actualSpyName = android.text.TextUtils.join(", ", actualSpyNamesList);

                Intent intent = new Intent(GameActivity.this, VotingActivity.class);
                intent.putStringArrayListExtra("ROLES", playerRoles);
                intent.putExtra("SECRET_WORD", secretWord);
                intent.putStringArrayListExtra("PLAYER_NAMES", playerNames);
                intent.putExtra("ACTUAL_SPY_NAME", actualSpyName);
                startActivity(intent);
            }
        });
    }

    private void fetchRandomWord() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT word_bn, word_en FROM words WHERE category_id = ? ORDER BY RANDOM() LIMIT 1", new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String wordBn = cursor.getString(cursor.getColumnIndex("word_bn"));
            @SuppressLint("Range") String wordEn = cursor.getString(cursor.getColumnIndex("word_en"));
            secretWord = wordBn + "\n(" + wordEn + ")";
        }
        cursor.close();
        db.close();
    }

    private void assignRoles() {
        for (int i = 0; i < (totalPlayers - totalSpies); i++) {
            playerRoles.add(secretWord);
        }
        for (int i = 0; i < totalSpies; i++) {
            playerRoles.add("Tumi Spy!\n(You are the SPY)");
        }
        Collections.shuffle(playerRoles);
    }

    private void updateUI() {
        tvPlayerTurn.setText(playerNames.get(currentPlayerIndex));
        tvInstruction.setText("Card e tap kore nijer role dekho");
        tvSecretWord.setText("TAP HERE");
        isRevealed = false;
        cardReveal.setClickable(true);
        btnNextPlayer.setVisibility(View.GONE);
    }
}