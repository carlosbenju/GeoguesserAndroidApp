package cat.itb.geoguesser;

import androidx.lifecycle.ViewModel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizViewModel extends ViewModel {
    private QuestionModel[] questionBank = {new QuestionModel("¿Cual es la cápital de España?", "Madrid", "Barcelona", "París", "Málaga", "Madrid"),
            new QuestionModel("¿Cual de los siguientes colores NO está en la bandera de Italia?", "Amarillo","Rojo", "Blanco", "Verde", "Amarillo"),
            new QuestionModel("¿Cual es la cápital de Alemánia?", "Munich", "Frankfurt", "Berlín", "Bruselas", "Berlín"),
            new QuestionModel("¿Cual es la cápital de Reino Unido?", "Glasgow", "Liverpool", "Birmingham", "Londres", "Londres"),
            new QuestionModel("¿Cual de los siguientes colores SI está en la bandera de Bélgica?", "Verde", "Amarillo", "Azul", "Blanco", "Blanco"),
            new QuestionModel("¿Cual es la cápital de Francia?", "Murcia", "Lyon", "Marsella", "París", "París"),
            new QuestionModel("¿Cual de los siguientes colores NO está en la bandera de Francia?", "Azul", "Rojo", "Blanco", "Negro", "Negro"),
            new QuestionModel("¿Cual es la cápital de Paises Bajos?", "Pais Bajo", "Bruselas", "Amsterdam", "Aruba", "Amsterdam"),
            new QuestionModel("¿Cual de los siguientes colores SI está en la bandera de Escocia?", "Azul", "Verde", "Amarillo","Negro", "Azul"),
            new QuestionModel("¿Cual de los siguientes colores NO está en la bandera de Alemania?", "Rojo", "Amarillo", "Blanco", "Negro", "Blanco")};
    final int TOTAL_QUESTIONS = 10;
    int contadorViewModel = 0;
    private Integer [] randomQuestions = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private Integer [] randomOptions = {0, 1, 2, 3};
    double score = 0;
    int currentOptions = -1;

    private int current = 0;
    private int contadorPistas = 0;

    public void noRepeatChoices(){
        List<Integer> intListNoRepeatChoices = Arrays.asList(randomOptions);
        Collections.shuffle(intListNoRepeatChoices);
    }

    public void noRepeatQuestions(){
        List<Integer> intListNoRepeatQuestions = Arrays.asList(randomQuestions);
        Collections.shuffle(intListNoRepeatQuestions);
    }

    public String getQuestionString() {
        return questionBank[randomQuestions[current]].getQuestion();
    }

    public QuestionModel getQuestion(){  //Retorna una pregunta diferente cada vez que se llama al método
        return questionBank[randomQuestions[current]];
    }

    public int nextCurrent(){  //Aumenta en 1 la variable actual para cada vez lanzar una pregunta distinta
        return current++;
    }

    public int getCurrent(){  //Retorna el valor de actual
        return current;
    }

    public int getTotalQuestions(){ //Retorna el número total de preguntas
        return TOTAL_QUESTIONS;
    }

    public void resetQuestions(){ //Inicializa de nuevo a 0 la variable actual
        current = 0;
    }

    public String getChoiceQuestion(){
        currentOptions++;
        return getQuestion().getOptions(randomOptions[currentOptions]);
    }

    public void resetOptions(){ //Inicializa la variable actualCohoice para que cada vez el orden de las opciones de respuesta sea distinto
        currentOptions = -1;
    }

    public void askHint(){ //Aumenta el contador de pistas
        contadorPistas++;
    }

    public int getContadorPistas(){ //Retorna el número de pistas
        return contadorPistas;
    }

    public void resetHints(){  //Reinicia el contador de pistas a 0
        contadorPistas = 0;
    }

    public int getContadorViewModel(){
        return contadorViewModel;
    }

    public void setContadorGeoViewModelGrow(){
        contadorViewModel = 1;
    }

    public void increaseScore(){ //Aumenta la puntuacion en 1
        score++;
    }

    public void decreaseScore(){ //Disminuye la puntuación en 0.5
        score = score - 0.5;
    }

    public double getScore(){ //retorna la puntuación final
        double aux;
        if (score < 0){
            aux = 0;
        }else{
            aux = score;
        }
        return aux * 10;
    }

    public void resetScore(){ //Reinicia la puntuación a 0
        score = 0;
    }



}




