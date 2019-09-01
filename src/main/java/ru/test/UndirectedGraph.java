package ru.test;

public class UndirectedGraph<T> extends Graph<T> {
    @Override
    protected void addEdgeConnection(Vertex<T> source, Vertex<T> destination) {
        adjacencyMap.get(source).add(destination);
        adjacencyMap.get(destination).add(source);
    }
}
