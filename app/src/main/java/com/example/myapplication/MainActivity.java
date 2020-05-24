package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.BitSet;

import static com.example.myapplication.AppUtil.CHANGE_LANGUAGE;
import static com.example.myapplication.AppUtil.originalMatrix;

public class MainActivity extends AppCompatActivity implements callBack {
    int[][] matrix;
    int[][] gameMatrix;
    TextView selectLanguage;
    TextView gameLevel;
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, close;
    TextView oldView = null;

    int[][] convertStringtoIntArray(String input) {

        int output[][] = new int[9][9];
        input = input.replace("[", "");
        input = input.replace("]", "");
        input = input.replace(",", "");

        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; ) {
                if (((int) input.charAt(count)) >= 48 && ((int) input.charAt(count)) <= 57) {
                    output[i][j] = ((int) input.charAt(count)) - 48;
                    j++;
                }
                count++;
            }
        }
        return output;
    }

    void setIds() {

        t1 = findViewById(R.id.one);
        t2 = findViewById(R.id.two);
        t3 = findViewById(R.id.three);
        t4 = findViewById(R.id.four);
        t5 = findViewById(R.id.five);
        t6 = findViewById(R.id.six);
        t7 = findViewById(R.id.seven);
        t8 = findViewById(R.id.eight);
        t9 = findViewById(R.id.nine);
        close = findViewById(R.id.close);
        gameLevel = findViewById(R.id.level);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int x = AppUtil.getSharedPreferencesInt(AppUtil.CHANGE_LANGUAGE, this);
        LocaleLanguage.updateLanguage(x, this);
        setContentView(R.layout.activity_main);
        setIds();


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //hide the title bar


        startGame();


    }

    void startGame() {
        String y = AppUtil.getSharedPreferencesString(originalMatrix, this);
        //   y = null;

        if (y == null) {
            int N = 9, K = 20;
            Sudoku sudoku = new Sudoku(N, K, this);
            sudoku.fillValues();
            matrix = sudoku.mat;
            gameMatrix = sudoku.mat;
            AppUtil.putSharedPreferences(originalMatrix, Arrays.deepToString(sudoku.mat), this);
            AppUtil.putSharedPreferences(AppUtil.gamelMat, Arrays.deepToString(sudoku.mat), this);

        } else {
            String gM = AppUtil.getSharedPreferencesString(AppUtil.gamelMat, this);
            matrix = convertStringtoIntArray(y);
            gameMatrix = convertStringtoIntArray(gM);
        }
        setData();
        setOnclickListenfornumbers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String gM = AppUtil.getSharedPreferencesString(AppUtil.gamelMat, this);
        String y = AppUtil.getSharedPreferencesString(originalMatrix, this);
        matrix = convertStringtoIntArray(y);
        gameMatrix = convertStringtoIntArray(gM);

    }

    @Override
    protected void onPause() {
        super.onPause();
        AppUtil.putSharedPreferences(originalMatrix, Arrays.deepToString(matrix), this);
        AppUtil.putSharedPreferences(AppUtil.gamelMat, Arrays.deepToString(gameMatrix), this);
    }

    void setOnclickListenfornumbers() {
        setListinerForView(t1, 1);
        setListinerForView(t2, 2);
        setListinerForView(t3, 3);
        setListinerForView(t4, 4);
        setListinerForView(t5, 5);
        setListinerForView(t6, 6);
        setListinerForView(t7, 7);
        setListinerForView(t8, 8);
        setListinerForView(t9, 9);
        setListinerForView(close, 19);
    }

    void setListinerForView(TextView textView, final int number) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNumber(number);
            }
        });
    }

    void setNumber(int number) {
        if (oldView != null) {
            String tag = oldView.getTag().toString();
            int ijValue = Integer.parseInt(tag);
            int j = ijValue % 10;
            int i = ijValue / 10;
            if (number == 19) {
                oldView.setBackgroundResource(R.drawable.rect);
                oldView.setText("");
                gameMatrix[i - 1][j - 1] = 0;
            } else {
                oldView.setBackgroundResource(R.drawable.filled_rectangle);
                oldView.setText(getNumber(number));
                gameMatrix[i - 1][j - 1] = number;

            }
            AppUtil.putSharedPreferences(originalMatrix, Arrays.deepToString(matrix), this);
            AppUtil.putSharedPreferences(AppUtil.gamelMat, Arrays.deepToString(gameMatrix), this);
        }

        if (!checkIsAnyEmptyThere()) {
            AppUtil.putSharedPreferences(originalMatrix, null, this);
            if (checkIsGameCompleted(gameMatrix)) {
                int level = AppUtil.getSharedPreferencesInt(AppUtil.level, this);
                if (level == -1) {
                    level = 1;
                }
                level++;
                AppUtil.putSharedPreferences(AppUtil.level, level, this);
                startGame();
            }
        }
    }

    boolean checkIsAnyEmptyThere() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameMatrix[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkIsGameCompleted(int[][] board) {

        //Check rows and columns
        for (int i = 0; i < board.length; i++) {
            BitSet bsRow = new BitSet(9);
            BitSet bsColumn = new BitSet(9);
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0 || board[j][i] == 0) continue;
                if (bsRow.get(board[i][j] - 1) || bsColumn.get(board[j][i] - 1))
                    return false;
                else {
                    bsRow.set(board[i][j] - 1);
                    bsColumn.set(board[j][i] - 1);
                }
            }
        }
        //Check within 3 x 3 grid
        for (int rowOffset = 0; rowOffset < 9; rowOffset += 3) {
            for (int columnOffset = 0; columnOffset < 9; columnOffset += 3) {
                BitSet threeByThree = new BitSet(9);
                for (int i = rowOffset; i < rowOffset + 3; i++) {
                    for (int j = columnOffset; j < columnOffset + 3; j++) {
                        if (board[i][j] == 0) continue;
                        if (threeByThree.get(board[i][j] - 1))
                            return false;
                        else
                            threeByThree.set(board[i][j] - 1);
                    }
                }
            }
        }
        return true;
    }

    void setData() {
        selectLanguage = findViewById(R.id.change_language);
        selectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguageOptions();
            }
        });

        int level = AppUtil.getSharedPreferencesInt(AppUtil.level, this);
        if (level == -1) {
            level = 1;
        }

        gameLevel.setText(getString(R.string.level) + " - " + level);

        View overlay = findViewById(R.id.parent);

        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        TableLayout tlayout = findViewById(R.id.table);


        int margin = 5;
        tlayout.removeAllViews();
        for (int i = 1; i < 10; i++) {
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;

            TableRow row = new TableRow(this.getApplicationContext());
            row.setLayoutParams(params);
            row.setWeightSum(9);

            for (int j = 1; j < 10; j++) {
                TableRow.LayoutParams params1 = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                params1.weight = 1.0f;

                int val = 0;
                if (i % 9 == 0 && j % 9 == 0) {
                    params1.setMargins(val, val, val, val);
                } else if (i % 9 == 0 && j % 3 == 0) {
                    params1.setMargins(val, val, margin, val);
                } else if (j % 9 == 0) {
                    params1.setMargins(val, val, val, val);
                } else if (i % 3 == 0 && j % 3 == 0) {
                    params1.setMargins(val, val, margin, margin);
                } else if (j % 3 == 0) {
                    params1.setMargins(val, val, margin, val);
                }


                final TextView text = new TextView(this.getApplicationContext());
                text.setLayoutParams(params1);
                text.setSingleLine(true);
                text.setMaxLines(1);
                text.setEllipsize(TextUtils.TruncateAt.END);


//                LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) text.getLayoutParams();
//                params.height = 70;
//                params.width = 70;
//                text.setLayoutParams(params2);

                text.setTextSize(30);
                text.setText(" " + getNumber(gameMatrix[i - 1][j - 1]) + " ");
                text.setMinWidth(80);
                text.setText(getNumber(gameMatrix[i - 1][j - 1]));
                text.setBackgroundResource(R.drawable.rect);


                if (getNumber(matrix[i - 1][j - 1]).equals("")) {
                    text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (oldView == null) {
                                oldView = (TextView) v;

                            } else {
                                if (oldView.getText().toString().equals("  ") || oldView.getText().toString().equals("")) {
                                    oldView.setBackgroundResource(R.drawable.parent_background_drawable);
                                } else {
                                    oldView.setBackgroundResource(R.drawable.filled_rectangle);
                                }
                                oldView = (TextView) v;

                            }
                            v.setBackgroundResource(R.drawable.rect);
                        }
                    });
                }
                text.setBackgroundResource(R.drawable.parent_background_drawable);
                text.setTextColor(getResources().getColor(R.color.white));
                text.setGravity(Gravity.CENTER);
                text.setTag("" + i + j);
                text.setTextSize(30);
                if (matrix[i - 1][j - 1] != gameMatrix[i - 1][j - 1]) {
                    text.setBackgroundResource(R.drawable.filled_rectangle);
                }

                if (oldView == null) {
                    if (getNumber(matrix[i - 1][j - 1]).equals("")) {
                        oldView = text;
                        oldView.setBackgroundResource(R.drawable.rect);
                    }
                }
                row.addView(text);
            }
            tlayout.addView(row);
        }


    }

    String getNumber(int i) {
        if (i == 1) {
            return getString(R.string.one);
        } else if (i == 2) {
            return getString(R.string.two);
        } else if (i == 3) {
            return getString(R.string.three);
        } else if (i == 4) {
            return getString(R.string.four);
        } else if (i == 5) {
            return getString(R.string.five);
        } else if (i == 6) {
            return getString(R.string.six);
        } else if (i == 7) {
            return getString(R.string.seven);
        } else if (i == 8) {
            return getString(R.string.eight);
        } else if (i == 9) {
            return getString(R.string.nine);
        }
        return "";
    }

    private void selectLanguageOptions() {
        int selectedlanguage = AppUtil.getSharedPreferencesInt(CHANGE_LANGUAGE, this);

        if (selectedlanguage == -1) {
            selectedlanguage = 0;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Language");

        builder.setItems(new LocaleLanguage(this).lang, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AppUtil.putSharedPreferences(CHANGE_LANGUAGE, which, MainActivity.this);
                LocaleLanguage.updateLanguage(which, MainActivity.this);
                dialog.dismiss();
                AppUtil.restart(MainActivity.this);

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void sudokoCreationCompleted() {
        setData();
    }
}
