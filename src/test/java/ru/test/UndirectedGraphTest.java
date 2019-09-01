package ru.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UndirectedGraphTest extends BaseGraphTest<UndirectedGraph<Integer>> {

    @Test
    public void addingEdgeShouldAddOppositeEdge() {
        Graph<Integer> graph = createEmptyGraph();

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex2);

        var path = graph.getPath(vertex1, vertex2);
        var oppositePath = graph.getPath(vertex2, vertex1);

        assertEquals(Arrays.asList(vertex1, vertex2), path);
        assertEquals(Arrays.asList(vertex2, vertex1), oppositePath);
    }

    @Test
    public void addExistingEdgeShouldNotThrowException() {
        Graph<Integer> graph = new UndirectedGraph<>();

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);

        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex2, vertex1);
    }


    @DataProvider
    public Object[][] vertexesToFindPath() {
        return new Object[][]{
                {vertex5, vertex7, false, null},
                {vertex6, vertex8, true, 1},
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

        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex1, vertex3);
        graph.addEdge(vertex1, vertex4);
        graph.addEdge(vertex2, vertex4);
        graph.addEdge(vertex3, vertex4);
        graph.addEdge(vertex4, vertex5);
        graph.addEdge(vertex6, vertex7);
        graph.addEdge(vertex7, vertex8);
        graph.addEdge(vertex6, vertex8);
        graph.addEdge(vertex8, vertex9);
        graph.addEdge(vertex3, vertex5);

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
    public UndirectedGraph<Integer> createEmptyGraph() {
        return new UndirectedGraph<>();
    }
}
