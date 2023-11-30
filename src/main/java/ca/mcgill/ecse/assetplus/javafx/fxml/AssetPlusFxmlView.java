package ca.mcgill.ecse.assetplus.javafx.fxml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AssetPlusFxmlView extends Application {
  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  private static AssetPlusFxmlView instance;
  private List<Node> refreshableNodes = new ArrayList<>();

  @Override
  public void start(Stage primaryStage) {
    instance = this;
    try {
      //var root = (Pane) FXMLLoader.load(getClass().getResource("MainPage.fxml"));
      // TODO: idk wtf is going on, just edit the aboslute path so it works on your pc
      String absolutePath = "D:\\McGillU2\\ECSE 223\\ecse223-group-project-p15\\src\\main\\java\\ca\\mcgill\\ecse\\assetplus\\javafx\\fxml\\MainPage.fxml";
      var root = (Pane) FXMLLoader.load(new File(absolutePath).toURI().toURL());
      //root.setStyle("-fx-base: rgba(20, 20, 20, 255);"); // Dark mode
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
    for (var node: nodes) {
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
