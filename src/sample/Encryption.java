package sample;

import java.util.ArrayList;

public class Encryption {

    private final ArrayList<Character> list;
    private ArrayList<Character> shuffledList;
    private char[] letters;

    public Encryption() {
        list = new ArrayList<>();
        shuffledList = new ArrayList<>();
        char character = ' ';

        for (int i=32; i< 127; i++) {
            list.add(character);
            character++;
        }
    }

    public String encrypt(String message) {
        letters = message.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                if (letters[i] == list.get(j)) {
                    letters[i] = shuffledList.get(j);
                    break;
                }
            }
        }
        return new String(letters);
    }

    public String decrypt(String message) {
        letters = message.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < shuffledList.size(); j++) {
                if (letters[i] == shuffledList.get(j)) {
                    letters[i] = list.get(j);
                    break;
                }
            }
        }
        return new String(letters);
    }

    public void setKey(ArrayList<Character> key) {
        shuffledList = key;
    }

}
