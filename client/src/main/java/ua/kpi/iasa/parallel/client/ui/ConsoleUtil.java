package ua.kpi.iasa.parallel.client.ui;

import java.util.List;
import java.util.Scanner;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ConsoleUtil {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void printGreetings() {
        System.out.println("Hi, it is Indexing application.");
        System.out.println("There are 2000 files with feedbacks from IMDB.");
        System.out.println("At first you have to run indexing, then get data from index by word\n");
    }

    public static int chooseMenuOptions() {
        System.out.println("\n|--------Menu points--------|");
        System.out.println("1. Run index.");
        System.out.println("2. Get texts by keyword.");
        System.out.println("0. Exit");
        System.out.print("\nEnter menu point number: ");
        return SCANNER.nextInt();
    }

    public static int handleIndexFiles() {
        System.out.println("You have picked indexing");
        System.out.print("Enter number of threads for indexing: ");
        return SCANNER.nextInt();
    }

    public static String handleGetTextsByWord() {
        System.out.println("You have picked getting texts by word");
        System.out.print("Enter keyword: ");
        SCANNER.nextLine();
        return SCANNER.nextLine();
    }

    public static void sendMessage(String message) {
        System.out.println("\n" + message);
    }

    public static void showFileNamesForKeyword(String keyword, List<String> textsByWord) {
        System.out.printf("\nFor keyword '%s' there are %d files\n", keyword, textsByWord.size());
        textsByWord.forEach(text -> System.out.printf("'{%s}'\n", text));
        System.out.println();
    }
}
