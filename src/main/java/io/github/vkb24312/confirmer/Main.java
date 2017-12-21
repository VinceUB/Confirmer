package io.github.vkb24312.confirmer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        System.out.println(
                "This program has last been written in JDK version 9.0.1\n"
                +"You are currently using JVM version: "
                + System.getProperty("java.version")
        );

        JFrame frame = new JFrame("The Allmighty Confirmer!");
        JPanel panel = new JPanel();
        final JTextArea field = new JTextArea("Placeholder text placeholder text placeholder \ntext", 2,25);
        JLabel description = new JLabel("Type your questions to the program here");
        final JTextArea textArea = new JTextArea("I am a computer. Ask me your questions in the \nbox above", 2, 25);
        JButton submit = new JButton("Submit");

        textArea.setEditable(false);
        frame.setVisible(true);
        frame.add(panel);
        panel.add(description);
        panel.add(field);
        panel.add(textArea);
        panel.add(submit);
        frame.setSize(350, 500);

        textArea.setPreferredSize(new Dimension(300, 100));
        field.setPreferredSize(new Dimension(300, 100));
        frame.setDefaultCloseOperation(3);

        submit.addActionListener(e -> {
            String submission = field.getText().toLowerCase();

            if(submission.startsWith("are you ")){
                StringBuilder sb = new StringBuilder(submission);
                if(submission.charAt(submission.length()-1)=='?') {
                    sb.deleteCharAt(submission.length()-1);
                }
                sb.append("!");
                sb.delete(0, 8);
                textArea.setText("Of course I am " + sb.toString());
            } else if(submission.startsWith("am i ")){
                StringBuilder sb = new StringBuilder(submission);
                if(submission.charAt(submission.length()-1)=='?') {
                    sb.deleteCharAt(submission.length()-1);
                }
                sb.append("!");
                sb.delete(0, 5);
                textArea.setText("Of course you are " + sb.toString());
            } else if(submission.startsWith("is")){
                Scanner s = new Scanner(submission);
                s.useDelimiter(" ");
                s.next();
                String person = s.next();

                if(submission.substring(3, 6).equals("the")||submission.substring(3, 6).equals("her")||submission.substring(3, 6).equals("his")||submission.substring(3, 8).equals("their")||submission.substring(3, 6).equals("our")||submission.substring(3, 5).equals("my")) {
                    person = person + " " + s.next();
                } else if(submission.substring(3, 7).equals("that")||submission.substring(3, 7).equals("this")){

                    Scanner s1 = new Scanner(submission);
                    s1.next();
                    int i = 0;
                    while(s1.next().equals(wordlist.nounlist[i])){i++;}
                    if(i!=wordlist.nounlist.length){
                        person = person + " " + s.next();
                    }
                }
                StringBuilder sb = new StringBuilder(submission);
                if(submission.charAt(submission.length()-1)=='?') {
                    sb.deleteCharAt(submission.length()-1);
                }

                sb.delete(0, person.length()+3);

                String finalString = deme(sb.toString());
                person = deme(person);

                if(finalString.charAt(finalString.length()-1)=='?') {
                    finalString = new StringBuilder(finalString).replace(finalString.length() - 2, finalString.length() - 1, "!").toString();
                }

                finalString = "Of course " + person + "is" + finalString;

                textArea.setText(finalString);
            }

            textArea.setText(newLine(textArea.getText(), 300));

            field.setText("");
        });
    }

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
        while(i<=sb.length()){
            while (i < newSpaceOnLine) {
                i++;
            }
            if(sb.toString().length()>i) {
                sb.insert(i, "\n");
            }
            newSpaceOnLine = newSpaceOnLine + spaceOnEachLine;
        }

        return sb.toString();
    }
}
//TODO: Do something actually productive