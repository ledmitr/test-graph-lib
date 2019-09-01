package ru.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public abstract class BaseGraphTest<T extends Graph<Integer>> {

    protected Vertex<Integer> vertex1 = new Vertex<>(1);
    protected Vertex<Integer> vertex2 = new Vertex<>(2);
    protected Vertex<Integer> vertex3 = new Vertex<>(3);
    protected Vertex<Integer> vertex4 = new Vertex<>(4);
    protected Vertex<Integer> vertex5 = new Vertex<>(5);
    protected Vertex<Integer> vertex6 = new Vertex<>(6);
    protected Vertex<Integer> vertex7 = new Vertex<>(7);
    protected Vertex<Integer> vertex8 = new Vertex<>(8);
    protected Vertex<Integer> vertex9 = new Vertex<>(9);

    public abstract T createEmptyGraph();

    @Test
    public void addExistingVertexShouldNotThrowException() {
        Graph<Integer> graph = createEmptyGraph();

        graph.addVertex(vertex1);
        graph.addVertex(vertex1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void invalidAddVertexParametersShouldThrowException() {
        Graph<Integer> graph = createEmptyGraph();
        graph.addVertex(null);
    }

    @DataProvider
    public Object[][] addEdgeParameters() {
        return new Object[][]{
                {null, null},
                {null, vertex2},
                {vertex1, null},
                {null, vertex3},
                {vertex4, null},
                {vertex4, vertex5}
        };
    }

    @Test(dataProvider = "addEdgeParameters", expectedExceptions = IllegalArgumentException.class)
    public void invalidAddEdgeParametersShouldThrowException(Vertex<Integer> source, Vertex<Integer> destination) {
        Graph<Integer> graph = createEmptyGraph();

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);

        graph.addEdge(source, destination);
    }
}
