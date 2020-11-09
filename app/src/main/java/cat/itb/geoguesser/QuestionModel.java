package cat.itb.geoguesser;


import java.util.Arrays;

public class QuestionModel {
    private String question;
    private String [] options = new String[4];
    private String answer;

    public QuestionModel(String question, String options_1, String options_2, String options_3, String options_4, String answer) {
        this.question = question;
        this.options[0] = options_1;
        this.options[1] = options_2;
        this.options[2] = options_3;
        this.options[3] = options_4;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptions(int pos) {
        return options[pos];
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "questionID=" + question +
                ", options=" + Arrays.toString(options) +
                ", correcta='" + answer + '\'' +
                '}';
    }
}
