package ca.mcgill.ecse.assetplus.javafx.fxml;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AssetPlusFxmlView extends Application {
    public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
    private static AssetPlusFxmlView instance;
    private List<Node> refreshableNodes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        try {
            var root = (Pane) FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            System.out.println("here");
            var scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.setTitle("AssetPlus");
            primaryStage.show();
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Register the node for receiving refresh events
    public void registerRefreshEvent(Node node) {
        refreshableNodes.add(node);
    }

    // Register multiple nodes for receiving refresh events
    public void registerRefreshEvent(Node... nodes) {
        for (var node : nodes) {
            refreshableNodes.add(node);
        }
    }

    // remove the node from receiving refresh events
    public void removeRefreshableNode(Node node) {
        refreshableNodes.remove(node);
    }

    // fire the refresh event to all registered nodes
    public void refresh() {
        for (Node node : refreshableNodes) {
            node.fireEvent(new Event(REFRESH_EVENT));
        }
    }

    public static AssetPlusFxmlView getInstance() {
        return instance;
    }
}
