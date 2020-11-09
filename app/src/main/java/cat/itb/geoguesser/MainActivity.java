package cat.itb.geoguesser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button opcion1;
    private Button opcion2;
    private Button opcion3;
    private Button opcion4;
    private Button hintButton;
    private Button answerBtn;
    private AlertDialog.Builder alertDialog;
    ProgressBar progressBar;
    private TextView pregunta;
    private TextView progessText;
    private CountDownTimer countDownTimer;
    private int dialogTitle;
    private long valorProgressBar;
    private QuizViewModel quizViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opcion1 = findViewById(R.id.option1);
        opcion2 = findViewById(R.id.option2);
        opcion3 = findViewById(R.id.option3);
        opcion4 = findViewById(R.id.option4);
        pregunta = findViewById(R.id.pregunta);
        hintButton = findViewById(R.id.hintButton);
        progessText = findViewById(R.id.questionProgress);
        progressBar = findViewById(R.id.progressBar);

        hintButton.setOnClickListener(this);

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        if (quizViewModel.getContadorViewModel() == 0){
            quizViewModel.setContadorGeoViewModelGrow();
            start();
        }else{
            if (quizViewModel.getContadorPistas() == 3){
                hintButton.setEnabled(false);
            }
            progressText();
            buttonEnable();
            showQuestion();
            if (savedInstanceState != null){
                timeProgress();
                valorProgressBar = savedInstanceState.getLong("progreso");
                countDownTimer.onTick(valorProgressBar);
                countDownTimer.start();
            }
        }
    }


    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("progreso", progressBar.getProgress());
    }

    public void start(){
        timeProgress();
        progressText();
        buttonEnable();
        quizViewModel.noRepeatChoices();
        quizViewModel.noRepeatQuestions();
        showQuestion();
    }

    public void showQuestion(){
        if (quizViewModel.getCurrent() < 10){
            pregunta.setText(quizViewModel.getQuestionString());
            opcion1.setText(quizViewModel.getChoiceQuestion());
            opcion2.setText(quizViewModel.getChoiceQuestion());
            opcion3.setText(quizViewModel.getChoiceQuestion());
            opcion4.setText(quizViewModel.getChoiceQuestion());

            quizViewModel.resetOptions();
        }else{
            buttonDisable();
            textFinal();
        }
    }


    public void progressText(){
        if (quizViewModel.getCurrent() < 10){
            progessText.setText("Pregunta "+ (quizViewModel.getCurrent() + 1)+ " de " + quizViewModel.getTotalQuestions());
        }
    }


    public void timeProgress(){
        countDownTimer = new CountDownTimer(10000, 100) {
            long timebar = 10000;
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) (millisUntilFinished/ 100));
            }

            @Override
            public void onFinish() {
                progressBar.setProgress((int) timebar);
                nextQuestion();
            }
        }.start();
    }

    public void buttonDisable(){
        opcion1.setEnabled(false);
        opcion2.setEnabled(false);
        opcion3.setEnabled(false);
        opcion4.setEnabled(false);
        hintButton.setEnabled(false);
    }


    public void buttonEnable(){
        opcion1.setEnabled(true);
        opcion2.setEnabled(true);
        opcion3.setEnabled(true);
        opcion4.setEnabled(true);
        if (quizViewModel.getContadorPistas() < 3){
            hintButton.setEnabled(true);
        }
    }

    public void nextQuestion(){
        quizViewModel.nextCurrent();
        countDownTimer.cancel();
        timeProgress();
        progressText();
        buttonEnable();
        showQuestion();
    }

    public void checkAnswer(View v){
        countDownTimer.cancel();
        answerBtn = findViewById(v.getId());
        String btnText = answerBtn.getText().toString();

        if (btnText.equalsIgnoreCase(quizViewModel.getQuestion().getAnswer())){
            dialogTitle = R.string.correcto;
            quizViewModel.increaseScore();
        }else{
            dialogTitle = R.string.incorrecto;
            quizViewModel.decreaseScore();

        }
        questionHit();
        buttonDisable();
    }

    public void pista(){
        countDownTimer.cancel();
        dialogTitle = R.string.hint;
        questionHit();
        if (quizViewModel.getCurrent() < 10){
            if (quizViewModel.getContadorPistas() > 2){
                hintButton.setEnabled(false);
            }
        }else{
            hintButton.setEnabled(true);
            quizViewModel.resetOptions();
        }
        buttonDisable();
    }

    public void questionHit(){
        countDownTimer.cancel();
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(dialogTitle);
        alertDialog.setMessage("Respuesta correcta:  " + quizViewModel.getQuestion().getAnswer());
        alertDialog.setPositiveButton(R.string.siguiente, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nextQuestion();
            }
        });
        alertDialog.show();
    }

    public void textFinal(){
        countDownTimer.cancel();
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("SCORE:   " + quizViewModel.getScore() + "/100");
        alertDialog.setMessage(R.string.retry);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quizViewModel.resetQuestions();
                quizViewModel.resetScore();
                quizViewModel.resetHints();
                start();
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hintButton) {
            pista();
            quizViewModel.askHint();
        }
    }
}