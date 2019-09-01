package ru.test;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Graph<T> {

    protected Map<Vertex<T>, Set<Vertex<T>>> adjacencyMap = new HashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    protected abstract void addEdgeConnection(Vertex<T> source, Vertex<T> destination);

    public void addEdge(Vertex<T> source, Vertex<T> destination) {
        try {
            lock.writeLock().lock();
            if (!adjacencyMap.containsKey(source) || !adjacencyMap.containsKey(destination)) {
                throw new IllegalArgumentException("Vertex is null");
            }

            addEdgeConnection(source, destination);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void addVertex(Vertex<T> vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex is null");
        }
        try {
            lock.writeLock().lock();
            adjacencyMap.putIfAbsent(vertex, new HashSet<>());
        } finally {
            lock.writeLock().unlock();
        }
    }

    public LinkedList<Vertex<T>> getPath(Vertex<T> source, Vertex<T> destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Vertex is null");
        }

        try {
            lock.writeLock().lock();

            if (isVertexNotExist(source) || isVertexNotExist(destination)) {
                throw new IllegalArgumentException("Vertex does not exist");
            }

            var path = new LinkedList<Vertex<T>>();
            path.add(source);
            if (source.equals(destination)) {
                return path;
            }

            var visited = new HashSet<Vertex<T>>();
            visited.add(source);

            var vertex = getNotVisitedNeighbor(source, visited);
            if (vertex == null) {
                return new LinkedList<>();
            }

            path.add(vertex);

            var current = vertex;

            while (path.size() > 0 || vertex != null) {
                if (current.equals(destination)) {
                    return path;
                }
                visited.add(current);
                vertex = getNotVisitedNeighbor(current, visited);
                if (vertex == null) {
                    // the path consists only of source vertex and doesn't have an unvisited neighbor
                    if (path.size() == 1) {
                        return new LinkedList<>();
                    }
                    current = goBack(path);
                } else {
                    current = goNext(path, vertex);
                }
            }

            return path;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Vertex<T> goBack(LinkedList<Vertex<T>> path) {
        path.removeLast();
        return path.getLast();
    }

    private Vertex<T> goNext(LinkedList<Vertex<T>> path, Vertex<T> neighbor) {
        path.add(neighbor);
        return neighbor;
    }

    private Vertex<T> getNotVisitedNeighbor(Vertex<T> source, Set<Vertex<T>> visited) {
        for (var vertex : adjacencyMap.get(source)) {
            if (!visited.contains(vertex)) {
                return vertex;
            }
        }
        return null;
    }

    private boolean isVertexNotExist(Vertex<T> vertex) {
        return !adjacencyMap.containsKey(vertex);
    }
}
