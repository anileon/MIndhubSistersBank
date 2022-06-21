package com.mindhub.homebanking.Utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static List<Integer> numbers = new ArrayList<>(); //creando propiedad privada y estatica

    public static int getRandomNumber( int min, int max) {

        int numberRandom ;
        do{
            numberRandom = (int)((Math.random() * (max - min)) + min);

        } while (numbers.contains(numberRandom));
        numbers.add(numberRandom);
        return numberRandom;
    }
}
