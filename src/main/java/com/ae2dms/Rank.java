package com.ae2dms;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Class of Ranking
 */
public class Rank {
    /**
     * data of one rank
     */
    public static class RankData {
        private int rank;
        private String name;
        private int level;
        private int move;

        public RankData(int rank, String name, int level, int move) {
            this.rank = rank;
            this.name = name;
            this.level = level;
            this.move = move;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getMove() {
            return move;
        }

        public void setMove(int move) {
            this.move = move;
        }
    }

    static String csvFile = "src/main/resources/rank/RankData.csv";
    CSVReader csvReader = null;

    public String curPlayer = "";

    public List<RankData> rankDataList = new ArrayList<>();

    /**
     * instance of rank
     */
    static Rank rank = new Rank();

    /**
     * default construct
     */
    private Rank() {
        try {
            csvReader = new CSVReader(new FileReader(csvFile));
            initRank();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * singleton
     * @return the instance of rank
     */
    public static Rank getInstance() {
        if (rank == null) {
            rank = new Rank();
        }
        return rank;
    }

    /**
     * init the rank from csv file
     * @throws IOException
     */
    private void initRank() throws IOException {
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            rankDataList.add(new RankData(Integer.parseInt(line[0]),
                    line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3])));
        }
    }

    /**
     * add an ranking data to rank list
     * @param name name of player
     * @param level level of his best
     * @param move min move when he get the next level
     */
    public static void addRanking(String name, int level, int move) {
        boolean inList = false;
        for (RankData rankData : rank.rankDataList) {
            if (rankData.name.equals(name)) {
                inList = true;
                if (level >= rankData.level && move < rankData.move) {
                    rankData.level = level;
                    rankData.move = move;
                } else {
                    break;
                }
            }
        }
        if (!inList) {
            rank.rankDataList.add(new RankData(0, name, level, move));
        }
        sortRankingList();
    }

    /**
     * sort the list by level and move
     */
    private static void sortRankingList() {
        rank.rankDataList.sort(((o1, o2) -> {
            if (o1.level == o2.level){
                if (o1.move == o2.move)
                    return 0;
                return o1.move < o2.move ? -1 : 1;
            }
            return o1.level < o2.level ? -1 : 1;
        }));
        for (int i = 0; i < rank.rankDataList.size(); i++) {
            rank.rankDataList.get(i).rank = i+1;
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csvFile));
            for (RankData rankData : rank.rankDataList) {
                String[] in = {String.valueOf(rankData.getRank()),
                        rankData.getName(),
                        String.valueOf(rankData.getLevel()),
                        String.valueOf(rankData.getMove())};
                bufferedWriter.write(String.join(",", in) + System.lineSeparator());
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
