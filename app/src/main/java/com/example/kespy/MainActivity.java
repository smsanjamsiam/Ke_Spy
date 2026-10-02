package com.example.kespy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTotalPlayers, etTotalSpies;
    Spinner spinnerCategory;
    Button btnStartGame;
    TextView tvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();

        etTotalPlayers = findViewById(R.id.etTotalPlayers);
        etTotalSpies = findViewById(R.id.etTotalSpies);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnStartGame = findViewById(R.id.btnStartGame);
        tvAbout = findViewById(R.id.tvAbout);

        String[] categories = {"Fruits - ফল", "Animals - প্রাণী", "Food - খাবার"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playersStr = etTotalPlayers.getText().toString();
                String spiesStr = etTotalSpies.getText().toString();

                if (playersStr.isEmpty() || spiesStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "সবগুলো ঘর পূরণ করো!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int players = Integer.parseInt(playersStr);
                int spies = Integer.parseInt(spiesStr);

                if (players < 3) {
                    Toast.makeText(MainActivity.this, "কমপক্ষে ৩ জন প্লেয়ার লাগবে!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spies >= players) {
                    Toast.makeText(MainActivity.this, "স্পাই এর সংখ্যা প্লেয়ারের চেয়ে কম হতে হবে!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int categoryId = spinnerCategory.getSelectedItemPosition() + 1;
                showPremiumPlayerNamesDialog(players, spies, categoryId);
            }
        });

        tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPremiumAboutDialog();
            }
        });
    }

    private void showPremiumPlayerNamesDialog(int players, int spies, int categoryId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(60, 70, 60, 60);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#1E293B"));
        bg.setCornerRadius(60f);
        mainLayout.setBackground(bg);

        TextView title = new TextView(this);
        title.setText("Enter Player Names");
        title.setTextSize(24f);
        title.setTextColor(Color.parseColor("#38BDF8"));
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 40);
        mainLayout.addView(title);

        ScrollView scrollView = new ScrollView(this);
        LinearLayout scrollLayout = new LinearLayout(this);
        scrollLayout.setOrientation(LinearLayout.VERTICAL);

        SharedPreferences sharedPreferences = getSharedPreferences("KeSpyPrefs", Context.MODE_PRIVATE);
        String savedNamesStr = sharedPreferences.getString("SavedNames", "");
        String[] savedNames = savedNamesStr.isEmpty() ? new String[0] : savedNamesStr.split(",");

        ArrayList<EditText> nameInputs = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            EditText input = new EditText(this);
            input.setHint("Player " + (i + 1) + " Name");
            input.setTextColor(Color.parseColor("#F8FAFC"));
            input.setHintTextColor(Color.parseColor("#64748B"));
            input.setPadding(40, 35, 40, 35);
            input.setTextSize(16f);

            if (i < savedNames.length && !savedNames[i].trim().isEmpty() && !savedNames[i].startsWith("Player ")) {
                input.setText(savedNames[i].trim());
            }

            GradientDrawable inputBg = new GradientDrawable();
            inputBg.setColor(Color.parseColor("#0F172A"));
            inputBg.setCornerRadius(30f);
            inputBg.setStroke(4, Color.parseColor("#38BDF8"));
            input.setBackground(inputBg);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 30);
            input.setLayoutParams(params);

            scrollLayout.addView(input);
            nameInputs.add(input);
        }
        scrollView.addView(scrollLayout);

        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 650);
        scrollView.setLayoutParams(scrollParams);
        mainLayout.addView(scrollView);

        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.HORIZONTAL);
        btnLayout.setWeightSum(2);
        btnLayout.setPadding(0, 40, 0, 0);

        Button btnCancel = new Button(this);
        btnCancel.setText("CANCEL");
        btnCancel.setTextColor(Color.WHITE);
        btnCancel.setTypeface(null, Typeface.BOLD);
        GradientDrawable cancelBg = new GradientDrawable();
        cancelBg.setColor(Color.parseColor("#F43F5E"));
        cancelBg.setCornerRadius(30f);
        btnCancel.setBackground(cancelBg);
        LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        cancelParams.setMargins(0, 0, 20, 0);
        btnCancel.setLayoutParams(cancelParams);

        Button btnGo = new Button(this);
        btnGo.setText("LET'S GO!");
        btnGo.setTextColor(Color.WHITE);
        btnGo.setTypeface(null, Typeface.BOLD);
        GradientDrawable goBg = new GradientDrawable();
        goBg.setColor(Color.parseColor("#10B981"));
        goBg.setCornerRadius(30f);
        btnGo.setBackground(goBg);
        LinearLayout.LayoutParams goParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        goParams.setMargins(20, 0, 0, 0);
        btnGo.setLayoutParams(goParams);

        btnLayout.addView(btnCancel);
        btnLayout.addView(btnGo);
        mainLayout.addView(btnLayout);

        AlertDialog dialog = builder.setView(mainLayout).create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnGo.setOnClickListener(v -> {
            ArrayList<String> playerNames = new ArrayList<>();
            StringBuilder namesBuilder = new StringBuilder();

            for (int i = 0; i < players; i++) {
                String name = nameInputs.get(i).getText().toString().trim();
                if (name.isEmpty()) {
                    name = "Player " + (i + 1);
                }
                playerNames.add(name);
                namesBuilder.append(name).append(",");
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("SavedNames", namesBuilder.toString());
            editor.apply();

            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("PLAYERS", players);
            intent.putExtra("SPIES", spies);
            intent.putExtra("CATEGORY_ID", categoryId);
            intent.putStringArrayListExtra("PLAYER_NAMES", playerNames);
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showPremiumAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(60, 80, 60, 60);
        mainLayout.setGravity(Gravity.CENTER);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#1E293B"));
        bg.setCornerRadius(60f);
        mainLayout.setBackground(bg);

        TextView title = new TextView(this);
        title.setText("About Creator");
        title.setTextSize(22f);
        title.setTextColor(Color.parseColor("#38BDF8"));
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 30);

        TextView name = new TextView(this);
        name.setText("S M Sanjam Siam");
        name.setTextSize(26f);
        name.setTextColor(Color.parseColor("#F8FAFC"));
        name.setTypeface(null, Typeface.BOLD);
        name.setGravity(Gravity.CENTER);

        TextView bio = new TextView(this);
        bio.setText("Computer Science Student & Tutor based in Bangladesh.\nStudying B.Sc. in CSE at BUBT.\nSoftware Developer | Machine Learning & AI Enthusiast.\n\nCreator of:\nAmar Notebook, Scholar Arc, Eco-Waste Locator, Ke Spy and other projects");
        bio.setTextSize(15f);
        bio.setTextColor(Color.parseColor("#94A3B8"));
        bio.setGravity(Gravity.CENTER);
        bio.setLineSpacing(0, 1.4f);
        bio.setPadding(0, 25, 0, 50);

        Button closeBtn = new Button(this);
        closeBtn.setText("AWESOME!");
        closeBtn.setTextColor(Color.WHITE);
        closeBtn.setTextSize(16f);
        closeBtn.setTypeface(null, Typeface.BOLD);
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setColor(Color.parseColor("#8B5CF6"));
        btnBg.setCornerRadius(30f);
        closeBtn.setBackground(btnBg);

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 130);
        closeBtn.setLayoutParams(btnParams);

        mainLayout.addView(title);
        mainLayout.addView(name);
        mainLayout.addView(bio);
        mainLayout.addView(closeBtn);

        AlertDialog dialog = builder.setView(mainLayout).create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        closeBtn.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}