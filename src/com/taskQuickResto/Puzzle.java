package com.taskQuickResto;

import java.util.*;

public class Puzzle implements PuzzleResolver {

    @Override
    public int[] resolve(int[] start) {
        int[] result = new int[] {1, 2, 3, 4, 0, 5, 6, 7};

        Queue<int[]> queue = new ArrayDeque<>();
        List<int[]> visited = new ArrayList<>();
        Map<int[], int[]> map = new HashMap<>();


        if (Arrays.equals(start, result)) return new int[0];

        queue.add(start);
        visited.add(start);

        while (queue.size() > 0) {
            int length = queue.size();
            for (int i = 0; i < length; i++) {
                int[] cur = queue.remove();
                for (int[] n: getNeighbors(cur)) {
                    if (contains(visited, n)) continue;
                    visited.add(n);
                    queue.add(n);
                    map.put(n, cur);

                    if (Arrays.equals(n, result)) {
                        i = length;
                        queue.clear();
                        break;
                    }
                }
            }
        }

        List<Integer> list = new ArrayList<>();
        int[] c = Arrays.copyOf(result, result.length);

        while (!Arrays.equals(start, c)) {
            int[] prev = get(map, c);
            int index = getIndexOfZero(c);
            list.add(prev[index]);
            c = Arrays.copyOf(prev, prev.length);
        }
        return toArray(list);
    }

    private Iterable<int[]> getNeighbors(int[] massive) {
        int index = getIndexOfZero(massive);
        LinkedList<int[]> neighbors = new LinkedList<>();

        switch (index) {
            case 0:
                neighbors.add(getNeighbor(massive, 0, 1));
                neighbors.add(getNeighbor(massive, 0, 2));
                break;
            case 1:
                neighbors.add(getNeighbor(massive, 1, 0));
                neighbors.add(getNeighbor(massive, 1, 2));
                neighbors.add(getNeighbor(massive, 1, 3));
                break;
            case 2:
                neighbors.add(getNeighbor(massive, 2, 0));
                neighbors.add(getNeighbor(massive, 2, 1));
                neighbors.add(getNeighbor(massive, 2, 5));
                break;
            case 3:
                neighbors.add(getNeighbor(massive, 3, 1));
                neighbors.add(getNeighbor(massive, 3, 4));
                neighbors.add(getNeighbor(massive, 3, 6));
                break;
            case 4:
                neighbors.add(getNeighbor(massive, 4, 3));
                neighbors.add(getNeighbor(massive, 4, 5));
                break;
            case 5:
                neighbors.add(getNeighbor(massive, 5, 2));
                neighbors.add(getNeighbor(massive, 5, 4));
                neighbors.add(getNeighbor(massive, 5, 7));
                break;
            case 6:
                neighbors.add(getNeighbor(massive, 6, 3));
                neighbors.add(getNeighbor(massive, 6, 7));
                break;
            case 7:
                neighbors.add(getNeighbor(massive, 7, 6));
                neighbors.add(getNeighbor(massive, 7, 5));
                break;
        }
        return neighbors;
    }

    private int[] getNeighbor(int[] massive, int i, int j) {
        int[] tmp = swap(massive, i, j);
        int[] result = Arrays.copyOf(tmp, tmp.length);
        swap(massive, i, j);
        return result;
    }

    private int[] get(Map<int[], int[]> map, int[] array) {
        for (Map.Entry<int[], int[]> m: map.entrySet()) {
            if (Arrays.equals(m.getKey(), array))
                return m.getValue();
        }
        return new int[0];
    }

    private boolean contains(List<int[]> list, int[] array) {
        for (int[] ints : list) {
            if (Arrays.equals(ints, array))
                return true;
        }
        return false;
    }

    private int[] swap(int[] massive, int i, int j) {
        int tmp = massive[i];
        massive[i] = massive[j];
        massive[j] = tmp;

        return massive;
    }

    private int getIndexOfZero(int[] massive) {
       for (int i = 0; i < massive.length; i++) {
           if (massive[i] == 0) {
               return i;
           }
       }
       throw new ArithmeticException();
    }

    private int[] toArray(List<Integer> list) {
        int[] tmp = new int[list.size()];
        for (int i = list.size() - 1; i >= 0; i--) {
            tmp[list.size() - i - 1] = list.get(i);
        }
        return tmp;
    }
}
