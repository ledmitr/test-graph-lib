package ru.test;

public class DirectedGraph<T> extends Graph<T> {
    @Override
    protected void addEdgeConnection(Vertex<T> source, Vertex<T> destination) {
        adjacencyMap.get(source).add(destination);
    }
}
