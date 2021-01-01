package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

public class SingleplayerAI {

    public static char localDetect(int id) {
        int[][] tempGrid = Board.getGrid();
        int cX = Board.getPlayers()[id].getLocationX();
        int cY = Board.getPlayers()[id].getLocationY();
        int dangerLevel = 0;
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                if (tempGrid[cX + i][cY + j] != 0)
                    dangerLevel++;
                if (i > -2 && i < 2 && j > -2 && j < 2 && tempGrid[cX + i][cY + j] != 0)
                    dangerLevel++;
            }
        }
        if (dangerLevel > 20) {
            return getIdealDirection1(id);
        } else {
            return getIdealDirection1(id);
        }
    }

    public static char getIdealDirection1(int id) {
        int[][] tempGrid = Board.getGrid();
        int cX = Board.getPlayers()[id].getLocationX();
        int cY = Board.getPlayers()[id].getLocationY();
        int w = 1;
        int a = 1;
        int s = 1;
        int d = 1;
        while (true) {
            if (tempGrid[cX][cY - w] != 0)
                break;
            w++;
        }
        while (true) {
            if (tempGrid[cX - a][cY] != 0)
                break;
            a++;
        }
        while (true) {
            if (tempGrid[cX][cY + s] != 0)
                break;
            s++;
        }
        while (true) {
            if (tempGrid[cX + d][cY] != 0)
                break;
            d++;
        }
        TreeMap<Integer, Character> sortMap = new TreeMap<>();
        sortMap.put(w, 'w');
        sortMap.put(a, 'a');
        sortMap.put(s, 's');
        sortMap.put(d, 'd');
        return sortMap.lastEntry().getValue();
    }

    public static Entry<Integer, Character> zackAlg(int id, int x, int y, char notD1, char notD2, int r) {
        if (r == 0) return new Entry<Integer,Character>() {
                @Override
                public Integer getKey() {
                    return 0;
                }

                @Override
                public Character getValue() {
                    return '0';
                }

                @Override
                public Character setValue(Character value) {
                    return '0';
                }
        };
        int[][] tempGrid = Board.getGrid();
        int w = 1;
        int a = 1;
        int s = 1;
        int d = 1;
        if (!(notD1 == 'w' || notD2 == 'w')) {
            while (true) {
                if (tempGrid[x][y - w] != 0) break;
                w += (zackAlg(id, x, y - w + 1, 'w', 's', r - 1)).getKey() + 1;
            }
        }
        if (!(notD1 == 'a' || notD2 == 'a')) {
            while (true) {
                if (tempGrid[x - a][y] != 0) break;
                a += zackAlg(id, x - a + 1, y, 'a', 'd', r - 1).getKey() + 1;
            }
        }
        if (!(notD1 == 's' || notD2 == 's')) {
            while (true) {
                if (tempGrid[x][y + s] != 0) break;
                s += zackAlg(id, x, y + s - 1, 's', 'w', r - 1).getKey() + 1;
            }
        }
        if (!(notD1 == 'd' || notD2 == 'd')) {
            while (true) {
                if (tempGrid[x + d][y] != 0) break;
                d += zackAlg(id, x + d - 1, y, 'd', 'a', r - 1).getKey() + 1;
            }
        }
        TreeMap<Integer, Character> sortMap = new TreeMap<>();
        sortMap.put(w, 'w');
        sortMap.put(a, 'a');
        sortMap.put(s, 's');
        sortMap.put(d, 'd');
        return sortMap.lastEntry();
    }

    public static Entry<Integer, Character> zackAlg2(int id, int x, int y, int notD1, int notD2, int r) {
        if (r == 0) return new Entry<Integer,Character>() {
            @Override
            public Integer getKey() {
                return 0;
            }

            @Override
            public Character getValue() {
                return '0';
            }

            @Override
            public Character setValue(Character value) {
                return '0';
            }
        };
        int[][] tempGrid = Board.getGrid();
        int w = 1;
        int a = 1;
        int s = 1;
        int d = 1;
        if (!(notD1 == 'w' || notD2 == 'w')) {
            while (true) {
                if (tempGrid[x][y - w] != 0) {
                    w += zackAlg2(id, x, y - w + 1, 'w', 's', r - 1).getKey();
                    break;
                }
                w++;
            }
        }
        if (!(notD1 == 'a' || notD2 == 'a')) {
            while (true) {
                if (tempGrid[x - a][y] != 0) {
                    a += zackAlg2(id, x - a + 1, y, 'a', 'd', r - 1).getKey();
                    break;
                }
                a++;
            }
        }
        if (!(notD1 == 's' || notD2 == 's')) {
            while (true) {
                if (tempGrid[x][y + s] != 0) {
                    s += zackAlg2(id, x, y + s - 1, 's', 'w', r - 1).getKey();
                    break;
                }
                s++;
            }
        }
        if (!(notD1 == 'd' || notD2 == 'd')) {
            while (true) {
                if (tempGrid[x + d][y] != 0) {
                    d += zackAlg2(id, x + d - 1, y, 'd', 'a', r - 1).getKey();
                    break;
                }
                d++;
            }
        }
        TreeMap<Integer, Character> sortMap = new TreeMap<>();
        sortMap.put(w, 'w');
        sortMap.put(a, 'a');
        sortMap.put(s, 's');
        sortMap.put(d, 'd');
        return sortMap.lastEntry();
    }

    public static char direction = '0';
    private static boolean initial;

    public static char gauravAlg(int id) {
        int[][] tempGrid = Board.getGrid();
        int myX = Board.getPlayers()[id].getLocationX();
        int myY = Board.getPlayers()[id].getLocationY();
        int w = 1;
        int a = 1;
        int s = 1;
        int d = 1;
        int count = 0;

        while (true) {
            if (tempGrid[myX][myY - w] != 0) {
                break;
            }
            w++;
        }
        while (true) {
            if (tempGrid[myX - a][myY] != 0) {

                break;
            }
            a++;
        }
        while (true) {
            if (tempGrid[myX][myY + s] != 0) {

                break;
            }
            s++;
        }
        while (true) {
            if (tempGrid[myX + d][myY] != 0) {
                break;
            }
            d++;
        }
        TreeMap<Integer, Character> sortMap = new TreeMap<>();
        if (w > 1) {
            sortMap.put(w, 'w');
        } else {
            count++;
        }
        if (a > 1) {
            sortMap.put(a, 'a');
        } else {
            count++;
        }
        if (s > 1) {
            sortMap.put(s, 's');
        } else {
            count++;
        }
        if (d > 1) {
            sortMap.put(d, 'd');
        } else {
            count++;
        }
        if (count > 1) {
            initial = false;
        }

        if (direction == '0') {
            direction = sortMap.firstEntry().getValue();
            initial = true;
            return direction;
        }

		if (sortMap.size() < 3 && initial == false && !(direction == '0')) {
            if (direction=='a'||direction=='d') {
                direction=sortMap.get(Math.max(w,s));
            }
            else {
                direction=sortMap.get(Math.max(a,d));
            }
            initial =true;
        }     
       
        return direction;
    }

    static class Point implements Comparable {
        private int x;
        private int y;

        public Point(int xCoord, int yCoord) {
            this.x = xCoord;
            this.y = yCoord;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        @Override
	    public boolean equals(Object o) {
            if (this.x == ((Point)o).x && this.y == ((Point)o).y) return true;
            else return false;
	    }

        @Override
        public int compareTo(Object o) {
            if (this.x == ((Point)o).x && this.y == ((Point)o).y) return 0; //consistent with equals
            else if (this.x < ((Point)o).x) return -1;
            else if (this.x > ((Point)o).x) return 1;
            else if (this.y < ((Point)o).y) return -1;
            else if (this.y > ((Point)o).y) return 1;
            return 0; //stub
        } 
    }

    static class Stack {
        private ArrayList<Point> stack;

        public Stack() {

        }

        public void push(Point o) {
            stack.add(o);
        }

        public Point pop() {
            Point popper = stack.get(stack.size() - 1);
            stack.remove(popper);
            return popper;
        }

        public Point peek() {
            return stack.get(stack.size() - 1);
        }

        public boolean isEmpty() {
            return stack.size() == 0;
        }

        public ArrayList<Point> getList() {
            return stack;
        }
    }

    public static char getIdealDirection3(int id) {
        TreeMap<Integer, Character> openSpace = new TreeMap<Integer, Character>();

        int[][] tempGrid = Board.getGrid();
        int cX = Board.getPlayers()[id].getLocationX();
        int cY = Board.getPlayers()[id].getLocationY();

        HashMap<Character, Integer> validDirections = new HashMap<Character, Integer>();
        validDirections.put('w', 0);
        validDirections.put('a', 0);
        validDirections.put('s', 0);
        validDirections.put('d', 0);

        for (int i = 1; i < 3; i++) {
            if (tempGrid[cX][cY - i] != 0) {
                validDirections.put('w', i);
            } else {
                break;
            }
        }

        for (int i = 1; i < 3; i++) {
            if (tempGrid[cX - i][cY] != 0) {
                validDirections.put('a', i);
            } else {
                break;
            }
        }
        for (int i = 1; i < 3; i++) {
            if (tempGrid[cX][cY + i] != 0) {
                validDirections.put('s', i);
            } else {
                break;
            }
        }
        for (int i = 1; i < 3; i++) {
            if (tempGrid[cX + i][cY] != 0) {
                validDirections.put('d', i);
            } else {
                break;
            }
        }

        if (validDirections.get('w') > 0) {
            openSpace.put(fillSpace('w', cX, cY - validDirections.get('w')), 'w');
        }

        if (validDirections.get('a') > 0) {
            openSpace.put(fillSpace('a', cX - validDirections.get('a'), cY), 'a');
        }

        if (validDirections.get('s') > 0) {
            openSpace.put(fillSpace('s', cX, cY + validDirections.get('s')), 's');
        }

        if (validDirections.get('d') > 0) {
            openSpace.put(fillSpace('d', cX + validDirections.get('d'), cY), 'd');
        }
        return openSpace.lastEntry().getValue();

    }

    public static int fillSpace(char direction, int x, int y) {
        Stack stack = new Stack();
        stack.push(new Point(x, y));

        int[][] tempGrid = Board.getGrid();
        // creating U shapes
        if (direction == 'w') {
            tempGrid[x - 1][y] = -1;
            tempGrid[x + 1][y] = -1;
            tempGrid[x][y + 1] = -1;
        } 
        else if (direction == 'a') {
            tempGrid[x + 1][y] = -1;
            tempGrid[x][y + 1] = -1;
            tempGrid[x][y - 1] = -1;
        } 
        else if (direction == 's') {
            tempGrid[x - 1][y] = -1;
            tempGrid[x + 1][y] = -1;
            tempGrid[x][y - 1] = -1;
        } 
        else if (direction == 'd') {
            tempGrid[x - 1][y] = -1;
            tempGrid[x][y + 1] = -1;
            tempGrid[x][y + 1] = -1;
        }

        TreeSet<Point> visited = new TreeSet<Point>();

        while (!stack.isEmpty()) {
            Point currentPoint = stack.pop();
            visited.add(currentPoint);

            int tempX = currentPoint.getX();
            int tempY = currentPoint.getY();

            ArrayList<Point> adjacent = new ArrayList<Point>();
            adjacent.add(new Point(tempX - 1, tempY));
            adjacent.add(new Point(tempX, tempY + 1));
            adjacent.add(new Point(tempX + 1, tempY));
            adjacent.add(new Point(tempX, tempY - 1));

            for (Point p : adjacent) {
                if (p.getX() < Runner.GRID_SIZE && p.getY() < Runner.GRID_SIZE && p.getX() > 0 && p.getY() > 0) {
                    if (tempGrid[p.getX()][p.getY()] == 0) {
                        if (!visited.contains(p)) {
                            stack.push(p);
                        }
                    }
                }
            }
        }

        return visited.size();
    }
    
}