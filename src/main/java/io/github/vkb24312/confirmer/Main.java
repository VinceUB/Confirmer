package io.github.vkb24312.confirmer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        System.out.println(
                "For details such as our website and compiler version, check META-INF/MANIFEST.MF in the JAR archive\n\n"
                +"You are currently using JVM version: " + System.getProperty("java.version") + " on the OS \"" + System.getProperty("os.name") + "\""
        );


        //<editor-fold desc="JFrame setup">
        JFrame frame = new JFrame("The Allmighty Confirmer!");
        JPanel panel = new JPanel();
        final JTextArea field = new JTextArea("", 2,25);
        JLabel description = new JLabel("Type your questions to the program here");
        final JTextArea textArea = new JTextArea("I am a computer. Ask me your questions in the \nbox above", 2, 25);
        JButton submit = new JButton("Submit");
        JScrollPane scrollPane = new JScrollPane(textArea);

        textArea.setEditable(false);
        frame.setVisible(true);
        frame.add(panel);
        panel.add(description);
        panel.add(field);
        panel.add(scrollPane);
        panel.add(submit);
        frame.setSize(350, 500);

        textArea.setPreferredSize(new Dimension(300, 100));
        field.setPreferredSize(new Dimension(300, 100));
        frame.setDefaultCloseOperation(3);
        scrollPane.setVisible(true);
        scrollPane.setPreferredSize(new Dimension(300+scrollPane.getVerticalScrollBar().getPreferredSize().width, 100+scrollPane.getHorizontalScrollBar().getPreferredSize().height));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //</editor-fold>

        submit.addActionListener(e -> {
            String submission = field.getText().toLowerCase();

            if(submission.startsWith("are you ")){
                textArea.setText(are_you(submission));
            } else if(submission.startsWith("am i ")){
                textArea.setText(am_i(submission));
            } else if(submission.startsWith("is")){
                textArea.setText(is(submission));
            } else {
                textArea.setText(
                        "I have absolutely no idea what you just asked.\n" +
                        "Please use one of the following prefixes:\n" +
                        "\"Are you\"\n" +
                        "\"Am I\"\n" +
                        "\"Is\""
                );
            }

            textArea.setText(newLine(textArea.getText(), 300));
            textArea.setPreferredSize(goodJTextAreaSize(textArea));

            field.setText("");
        });
    }

    //<editor-fold desc="Utilities">
    private static String deme(String s){
        String[] myCheckerString = s.split(" ");
        int i = 0;
        for (String s1:myCheckerString) {
            switch (s1) {
                case "my":
                    s1 = "your";
                    break;
                case "mine":
                    s1 = "yours";
                    break;
                case "I":
                case "me":
                    s1 = "you";
                    break;
                case "your":
                    s1 = "my";
                    break;
                case "yours":
                    s1 = "mine";
                    break;
                case "you":
                    s1 = "I";
                    break;
            }
            myCheckerString[i] = s1;
            i++;
        }

        StringBuilder finalStringBuilder = new StringBuilder(" ");
        for (String s1:myCheckerString) {
            finalStringBuilder.append(s1).append(" ");
        }
        String finalString = finalStringBuilder.toString();

        finalString = new StringBuilder(finalString).deleteCharAt(0).toString();
        return finalString;
    }

    private static String newLine(String s, int spaceOnEachLine){
        FontMetrics fontMetrics = new FontMetrics(new JTextArea().getFont()) {};
        int size = (int) fontMetrics.getStringBounds(s, null).getWidth();

        int averageLetterSize = size/s.length();

        spaceOnEachLine = (spaceOnEachLine/averageLetterSize)- 0xa;

        StringBuilder sb = new StringBuilder(s);
        int newSpaceOnLine = spaceOnEachLine;
        int i = 0;
        label:
        while(i<=sb.length()){
            while (i < newSpaceOnLine & i < sb.length()) {
                if(sb.charAt(i)=='\n'){
                    newSpaceOnLine = newSpaceOnLine + i;
                    break label;
                }
                i++;
            }

            newSpaceOnLine = newSpaceOnLine + spaceOnEachLine;

            if(sb.length()>=i) {
                sb.insert(i, "\n");
            } else {
                System.out.print("\u0000");
                break;
            }
        }

        return sb.toString();
    }

    private static Dimension goodJTextAreaSize(JTextArea textArea){
        String text = textArea.getText();

        int lengthOfLongest = 0;
        int longestLine = 0;
        ArrayList lines = new ArrayList();
        int i = 0;
        int lineStart = 0;
        for (int j = 0; j < text.length(); j++) {
            if (text.charAt(j) == '\n') {
                if(j>lengthOfLongest){
                    lengthOfLongest = j;
                    longestLine = i;
                }
                if(lineStart<1) lines.add(i, text.substring(lineStart, j));
                else lines.add(i, text.substring(lineStart-1, j));
                i++;
                lineStart = j+1;
            }
        }

        int height;
        int width;
        FontMetrics fm = new FontMetrics(textArea.getFont()) {};
        width = (int) fm.getStringBounds((String) lines.get(longestLine), null).getWidth();

        height = i*((int) fm.getStringBounds((String) lines.get(longestLine), null).getWidth());

        return new Dimension(width, height);
    }
    //</editor-fold>

    //<editor-fold desc="Prefix reactions">
    private static String are_you(String s){
        StringBuilder sb = new StringBuilder(s);
        if(s.charAt(s.length()-1)=='?') {
            sb.deleteCharAt(s.length()-1);
        }
        sb.append("!");
        sb.delete(0, 8);
        return "Of course I am " + sb.toString();
    }

    private static String am_i(String s){
        StringBuilder sb = new StringBuilder(s);
        if(s.charAt(s.length()-1)=='?') {
            sb.deleteCharAt(s.length()-1);
        }
        sb.append("!");
        sb.delete(0, 5);
        return "Of course you are " + sb.toString();
    }

    private static String is(String s){
        Scanner s1 = new Scanner(s);
        s1.useDelimiter(" ");
        s1.next();
        String person = s1.next();

        if(s.substring(3, 6).equals("the")||s.substring(3, 6).equals("her")||s.substring(3, 6).equals("his")||s.substring(3, 8).equals("their")||s.substring(3, 6).equals("our")||s.substring(3, 5).equals("my")) {
            person = person + " " + s1.next();
        } else if(s.substring(3, 7).equals("that")||s.substring(3, 7).equals("this")){

            Scanner s2 = new Scanner(s);
            s2.next();
            int i = 0;
            while(s2.next().equals(wordlist.nounlist[i])){i++;}
            if(i!=wordlist.nounlist.length){
                person = person + " " + s1.next();
            }
        }
        StringBuilder sb = new StringBuilder(s);
        if(s.charAt(s.length()-1)=='?') {
            sb.deleteCharAt(s.length()-1);
        }

        sb.delete(0, person.length()+3);

        String finalString = deme(sb.toString());
        person = deme(person);

        if(finalString.charAt(finalString.length()-1)=='?') {
            finalString = new StringBuilder(finalString).replace(finalString.length() - 2, finalString.length() - 1, "!").toString();
        }

        finalString = "Of course " + person + "is" + finalString;

        return finalString;
    }
    //</editor-fold>
}





//TODO: Do something actually productive