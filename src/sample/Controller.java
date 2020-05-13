package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class Controller {

    @FXML
    public ComboBox choiceBox;

    @FXML
    private TextField resultField;

    @FXML
    private TextField cardLengthField;

    @FXML
    public void generateCardNumber(ActionEvent event) {
        String selectedBankBin = (String)choiceBox.getSelectionModel().getSelectedItem();
        int cardLength = Integer.parseInt(cardLengthField.getText());
        resultField.setVisible(true);
        resultField.setAlignment(Pos.CENTER);
        resultField.setText(generateCardNumberByParams(cardLength, selectedBankBin.split("-")[1]));
    }

    public String generateCardNumberByParams(int length, String cardBin) {
        final String NUMERIC_STRING = "0123456789";
        int cnt = length - 1;
        int[] results = new int[length];
        for (int j = 0; j < length; j++) {
            results[j] = (int) (NUMERIC_STRING.length() * Math.random());
        }

        for (int i = 0; i <= 5; i++) {
            results[i] = Character.getNumericValue(cardBin.charAt(i));
        }

        // Computing sum
        boolean dblFlag = true;
        int sum = 0;
        int dbl;
        while (cnt-- > 0) {
            if (dblFlag) {
                dbl = 2 * results[cnt];
                sum += (dbl > 9) ? (dbl % 10 + 1) : dbl;
            } else {
                sum += results[cnt];
            }
            dblFlag = !dblFlag;
        }
        // Add the check digit
        results[length - 1] = (9 * sum) % 10;

        // Returning result
        StringBuilder buff = new StringBuilder();
        Arrays.stream(results)
              .forEach(buff::append);
        return buff.toString();
    }
}