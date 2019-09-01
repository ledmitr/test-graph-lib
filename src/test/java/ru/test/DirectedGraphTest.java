package ru.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DirectedGraphTest extends BaseGraphTest<DirectedGraph<Integer>> {

    @Test
    public void addingEdgeShouldNotAddOppositeEdge() {
        Graph<Integer> graph = createEmptyGraph();

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex2);

        var path = graph.getPath(vertex1, vertex2);
        var oppositePath = graph.getPath(vertex2, vertex1);

        assertEquals(Arrays.asList(vertex1, vertex2), path);
        assertEquals(Collections.emptyList(), oppositePath);
    }

    @Test
    public void addExistingEdgeShouldNotThrowException() {
        Graph<Integer> graph = new DirectedGraph<>();

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);

        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex1, vertex2);
    }

    @DataProvider
    public Object[][] vertexesToFindPath() {
        return new Object[][]{
                {vertex5, vertex7, false, null},
                {vertex6, vertex8, true, 0},
                {vertex5, vertex3, true, 1},
                {vertex5, vertex2, true, 2},
                {vertex5, vertex5, true, 1},
                {vertex2, vertex5, true, 2},
                {vertex9, vertex6, true, 2}
        };
    }

    @Test(dataProvider = "vertexesToFindPath")
    public void pathShouldBeCalculatedCorrectly(Vertex<Integer> source, Vertex<Integer> destination,
                                                boolean pathShouldExist, Integer minPathSize) {
        Graph<Integer> graph = createEmptyGraph();

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);
        graph.addVertex(vertex5);
        graph.addVertex(vertex6);
        graph.addVertex(vertex7);
        graph.addVertex(vertex8);
        graph.addVertex(vertex9);

        graph.addEdge(vertex2, vertex1);
        graph.addEdge(vertex2, vertex4);
        graph.addEdge(vertex1, vertex4);
        graph.addEdge(vertex3, vertex1);
        graph.addEdge(vertex3, vertex1);
        graph.addEdge(vertex3, vertex4);
        graph.addEdge(vertex3, vertex2);
        graph.addEdge(vertex4, vertex3);
        graph.addEdge(vertex4, vertex5);
        graph.addEdge(vertex5, vertex3);
        graph.addEdge(vertex6, vertex7);
        graph.addEdge(vertex8, vertex6);
        graph.addEdge(vertex8, vertex7);
        graph.addEdge(vertex9, vertex8);


        var path = graph.getPath(source, destination);

        System.out.println("source: " + source);
        System.out.println("destination: " + destination);
        System.out.println("path: " + path);

        if (pathShouldExist) {
            assertTrue(path.size() >= minPathSize);
        } else {
            assertTrue(path.isEmpty());
        }
    }

    @Override
    public DirectedGraph<Integer> createEmptyGraph() {
        return new DirectedGraph<>();
    }
}
