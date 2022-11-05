package chucknorris;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChuckNorris {
    private static final Scanner SCANNER = new Scanner(System.in);
    private String input;

    public void run() {
        String option;

        loop: while (true) {
            System.out.println("Please input operation (encode/decode/exit):");

            option = SCANNER.nextLine();

            switch (option) {
                case "encode":
                    System.out.println("Input string:");
                    readUserInput();
                    encrypt();
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    readUserInput();
                    decrypt();
                    break;
                case "exit":
                    System.out.println("Bye!");
                    break loop;
                default:
                    System.out.println("There is no '" + option + "' operation");
                    break;
            }
        }

        System.exit(0);
    }

    private void readUserInput() {
        input = SCANNER.nextLine();
    }

    private void encrypt() {
        StringBuilder result = new StringBuilder();
        int flag = -1;

        for (int i = 0; i < input.length(); i++) {
            for (int j = 6; j >= 0; j--) {
                int bit = input.charAt(i) >> j & 1;

                if (bit != flag) {
                    if (flag != -1) {
                        result.append(" ");
                    }

                    result.append((1 == bit) ? "0 " : "00 ");
                    flag = bit;
                }

                result.append("0");
            }
        }

        System.out.println("Encoded string:\n" + result);
    }

    private void decrypt() {
        if (checkZeros()) {
            StringBuilder result = new StringBuilder();
            boolean flag = true;
            List<String> binaryList = decryptText();


            for (String s : binaryList) {
                if (s.length() % 7 == 0) {
                    result.append((char) Integer.parseInt(s, 2));
                } else {
                    flag = false;

                    System.out.println("Encoded string is not valid.");
                }
            }

            if (flag) {
                System.out.println("Decoded string:\n" + result);
            }
        } else {
            System.out.println("Encoded string is not valid.");
        }
    }

    private boolean checkZeros() {
        String[] temp = input.split("\\s+");
        boolean flag = temp.length % 2 == 0;

        if (flag) {
            for (String s : temp) {
                flag = flag && s.equals("0".repeat(s.length()));
            }

            if (flag) {
                for (int i = 0; i < temp.length; i += 2) {
                    flag = flag && ("0".equals(temp[i]) || "00".equals(temp[i]));
                }
            }
        }

        return flag;
    }

    private List<String> decryptText() {
        String[] decryptText = input.split("\\s+");
        StringBuilder decryptedText = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        int counter = 0;
        List<String> binaryList = new ArrayList<>();

        for (int i = 0; i < decryptText.length - 1; i += 2) {
            if ("0".equals(decryptText[i])) {
                decryptedText.append("1".repeat(decryptText[i + 1].length()));
            } else if ("00".equals(decryptText[i])){
                decryptedText.append("0".repeat(decryptText[i + 1].length()));
            }
        }

        for (int i = 0; i < decryptedText.length(); i++) {
            temp.append(decryptedText.charAt(i));

            if (counter == 6) {
                binaryList.add(temp.toString());
                temp = new StringBuilder();
                counter = 0;
            } else {
                counter++;
            }
        }

        if (!binaryList.contains(temp.toString()) && !"".equals(temp.toString())) {
            binaryList.add(temp.toString());
        }

        return binaryList;
    }
}
