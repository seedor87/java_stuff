package ADAA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LevenshteinDist {

    public static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i < a.length(); i++) {
            costs[0] = i;
            int nw = i-1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                    a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = "In computer science, approximate string matching, "
                    + "\noften colloquially referred to as fuzzy string searching is "
                    + "\nthe technique of finding strings that match a pattern "
                    + "\napproximately rather than exactly. The problem of approximate "
                    + "\nstring matching is typically divided into two sub-problems: "
                    + "\nfinding approximate substring matches inside a given string and "
                    + "\nfinding dictionary strings that match the pattern approximately.";
        System.out.println("System Generated string to search is: \n" + text);

        String keyword;
        do {
            System.out.println("Enter the keyword to search for >> ");
            keyword = sc.nextLine();
            if(keyword.length() < 1) {
                System.out.println("quiting");
                break;
            }
            String[] data = text.split(" ");
            List<Integer> dist = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                dist.add(distance(data[i], keyword));
            }
            Collections.sort(dist);
            System.out.println("Did you mean? ");
            for (int i = 0; i < data.length; i++) {
                if(distance(data[i], keyword) == dist.get(0)) {
                    System.out.println(data[i] + " ");
                }
            }
        } while (true);
        sc.close();
    }
}