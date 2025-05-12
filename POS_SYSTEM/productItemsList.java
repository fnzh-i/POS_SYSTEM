package POS_SYSTEM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class productItemsList {
    public static void main(String[] args) {
        String ItemName = "Jack n' Jill Piattos";
        String ItemSize = "40g";
        String ItemImg = "Images/Sample Product Images/Piattos-Cheese-40g.png";
        double ItemPrice = 15.00;

        // Initialize a List<Object> named list1 with initial values.
        List<Object> list1 = new ArrayList<>(Arrays.asList());

        // Initialize three lists of objects: list2, list3, and list4.
        List<Object> list2 = new ArrayList<>(Arrays.asList(ItemName, ItemSize, ItemImg, ItemPrice));
        List<Object> list3 = new ArrayList<>(Arrays.asList("Hatdog", ItemSize, ItemImg, ItemPrice));
        List<Object> list4 = new ArrayList<>(Arrays.asList("Hamdog", ItemSize, ItemImg, ItemPrice));

        // Add the integer lists (list2, list3, list4) to the main list (list1).
        list1.add(list2);
        list1.add(list3);
        list1.add(list4);

        // Output the main list in a readable format.
        System.out.print("[");
        for (int i = 0; i < list1.size(); i++) {
            Object item = list1.get(i);
            if (item instanceof List) {
                // If the item is a List<Integer>, format it as "[item1, item2, ...]".
                List<Object> sublist = (List<Object>) item;
                System.out.print("[" + String.join(", ", sublist.stream().map(Object::toString).toArray(String[]::new)) + "]");
            } else {
                System.out.print(item);
            }

            // Add a comma and space if the current item is not the last one.
            if (i != list1.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

    }

    List<String> productList;

    public productItemsList() {
        productList = List.of(new String[0]);
    }

    public static void add(String s) {
    }

//    public List<Object> productItemsList(String itemName, String itemSize, String itemImg, double itemPrice) {
//        String ItemName = "Jack n' Jill Piattos";
//        String ItemSize = "40g";
//        String ItemImg = "Images/Sample Product Images/Piattos-Cheese-40g.png";
//        double ItemPrice = 15.00;
//
//        // Initialize a List<Object> named list1 with initial values.
//        List<Object> list1 = new ArrayList<>(Arrays.asList());
//
//        // Initialize three lists of integers: list2, list3, and list4.
//        List<Object> list2 = new ArrayList<>(Arrays.asList(ItemName, ItemSize, ItemImg, ItemPrice));
//        List<Object> list3 = new ArrayList<>(Arrays.asList(ItemName, ItemSize, ItemImg, ItemPrice));
//        List<Object> list4 = new ArrayList<>(Arrays.asList(ItemName, ItemSize, ItemImg, ItemPrice));
//
//
//        // Add the integer lists (list2, list3, list4) to the main list (list1).
//        list1.add(list2);
//        list1.add(list3);
//        list1.add(list4);
//
//        // Output the main list in a readable format.
//        System.out.print("[");
//        for (int i = 0; i < list1.size(); i++) {
//            Object item = list1.get(i);
//            if (item instanceof List) {
//                // If the item is a List<Integer>, format it as "[item1, item2, ...]".
//                List<Object> sublist = (List<Object>) item;
//                System.out.print("[" + String.join(", ", sublist.stream().map(Object::toString).toArray(String[]::new)) + "]");
//            } else {
//                System.out.print(item);
//            }
//
//            // Add a comma and space if the current item is not the last one.
//            if (i != list1.size() - 1) {
//                System.out.print(", ");
//            }
//        }
//        System.out.println("]");
//        return list1;
//    }
}
