package com.example.kespy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;

public class VotingActivity extends AppCompatActivity {

    TextView tvVoteTitle, tvVoteSubtitle;
    RadioGroup rgSuspects;
    Button btnSubmitVote, btnPlayAgain;
    CardView cardVote, cardResult;
    TextView tvResultTitle, tvResultDesc, tvRealWord;

    ArrayList<String> playerRoles;
    ArrayList<String> playerNames;
    String secretWord;

    int[] voteCounts;
    int currentVotingIndex = 0;
    int totalPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        tvVoteTitle = findViewById(R.id.tvVoteTitle);
        tvVoteSubtitle = findViewById(R.id.tvVoteSubtitle);
        rgSuspects = findViewById(R.id.rgSuspects);
        btnSubmitVote = findViewById(R.id.btnSubmitVote);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        cardVote = findViewById(R.id.cardVote);
        cardResult = findViewById(R.id.cardResult);
        tvResultTitle = findViewById(R.id.tvResultTitle);
        tvResultDesc = findViewById(R.id.tvResultDesc);
        tvRealWord = findViewById(R.id.tvRealWord);

        playerRoles = getIntent().getStringArrayListExtra("ROLES");
        playerNames = getIntent().getStringArrayListExtra("PLAYER_NAMES");
        secretWord = getIntent().getStringExtra("SECRET_WORD");

        totalPlayers = playerNames.size();
        voteCounts = new int[totalPlayers];

        // রেডিও বাটন তৈরি করা
        for (int i = 0; i < totalPlayers; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(playerNames.get(i));
            rb.setTextColor(0xFFF8FAFC);
            rb.setTextSize(18f);
            rb.setPadding(20, 15, 20, 15);
            rgSuspects.addView(rb);
        }

        updateVotingScreen();

        btnSubmitVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgSuspects.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(VotingActivity.this, "কাকে সন্দেহ হয় সিলেক্ট করো!", Toast.LENGTH_SHORT).show();
                    return;
                }

                View radioButton = rgSuspects.findViewById(selectedId);
                int selectedSuspectIndex = rgSuspects.indexOfChild(radioButton);

                voteCounts[selectedSuspectIndex]++;
                currentVotingIndex++;

                if (currentVotingIndex < totalPlayers) {
                    Toast.makeText(VotingActivity.this, "Vote Done! ফোন পাস করো।", Toast.LENGTH_SHORT).show();
                    updateVotingScreen();
                } else {
                    calculateAndShowResult();
                }
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VotingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateVotingScreen() {
        tvVoteTitle.setText(playerNames.get(currentVotingIndex) + "'s Turn!");
        rgSuspects.clearCheck(); // আগের ভোট মুছে খালি করে দিবে
    }

    private void calculateAndShowResult() {
        int maxVotes = 0;
        for (int v : voteCounts) {
            if (v > maxVotes) {
                maxVotes = v;
            }
        }

        boolean isSpyCaught = false;
        StringBuilder highestVotedPlayers = new StringBuilder();

        for (int i = 0; i < totalPlayers; i++) {
            if (voteCounts[i] == maxVotes) {
                if (playerRoles.get(i).contains("Spy")) {
                    isSpyCaught = true;
                }
                highestVotedPlayers.append(playerNames.get(i)).append(", ");
            }
        }

        if (highestVotedPlayers.length() > 0) {
            highestVotedPlayers.setLength(highestVotedPlayers.length() - 2);
        }

        cardVote.setVisibility(View.GONE);
        cardResult.setVisibility(View.VISIBLE);
        tvRealWord.setText("আসল ওয়ার্ড ছিল:\n" + secretWord);

        if (isSpyCaught) {
            tvResultTitle.setText("Victory!");
            tvResultTitle.setTextColor(0xFF4ADE80);
            tvResultDesc.setText("সাবাশ! স্পাই ধরা পড়েছে।\nসবচেয়ে বেশি ভোট পেয়েছে: " + highestVotedPlayers.toString());
        } else {
            tvResultTitle.setText("Defeat!");
            tvResultTitle.setTextColor(0xFFF87171);
            tvResultDesc.setText("ভুল মানুষ! স্পাই জিতে গেছে।\nসবচেয়ে বেশি ভোট পেয়েছে: " + highestVotedPlayers.toString());
        }
    }
}