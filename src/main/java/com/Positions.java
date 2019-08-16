package com;

import com.exceptions.ArgInputException;
import com.exceptions.PositionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static com.TransactionRecord.createTransactionRecord;

public class Positions {
    private static final String QUIT = "quit";
    private static final int PERIOD = 60000;
    private static Map<String, Integer> positions = new ConcurrentHashMap<>();

    private static void readFile(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(Positions::updatePostionsByLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updatePositions(TransactionRecord record) {
        if (positions.get(record.getCurrencyCode()) != null) {
            int newAmount = positions.get(record.getCurrencyCode()) + record.getAmount();
            if (newAmount > 0) {
                positions.replace(record.getCurrencyCode(), newAmount);
            } else {
                if (newAmount == 0) {
                    positions.remove(record.getCurrencyCode());
                } else {
                    throw new PositionException(record.getCurrencyCode());
                }
            }
        } else {
            if (record.getAmount() < 0) {
                throw new PositionException(record.getCurrencyCode());
            }
            positions.put(record.getCurrencyCode(), record.getAmount());
        }
    }

    private static void displayPositions() {
        positions.forEach((String code, Integer amount) -> {System.out.println(code + " " + amount.toString());});
    }

    private static void updatePostionsByLine(String line) {
        updatePositions(createTransactionRecord(line));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                displayPositions();
            }
        }, 0, PERIOD);
        try {
            if (args.length > 1) {
                throw new ArgInputException();
            }
            if (args.length == 1) {
                readFile(args[0]);
            }
            System.out.println("You can start to type input:");
            while (true) {
                String line = bufferedReader.readLine();
                if (line.equals(QUIT)) {
                    System.exit(0);
                }
                updatePostionsByLine(line);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            t.cancel();
        }
    }
}
