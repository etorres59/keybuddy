package com.evantorreaudio;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private final HashMap<String, Button> keyMap = new HashMap<>();
    private final Map<KeyCode, String> shiftMap = new HashMap<>();
    private boolean shiftDown = false;
    private boolean capsLockDown = false;
    private final Set<String> Index_Tabs = Set.of("F", "J");
    private final String Index_Tab_Style = "-fx-border-color: black; -fx-border-width: 0 0 3 0;";

    {
        initializeShiftMap();
    }

    @Override
    public void start(Stage stage) {
        VBox keyboard = new VBox(10,
            createControlRow(stage),
            createRow(new String[] { "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "Delete" }, 60),
            createRowWithPrefix("Tab", new String[] { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\" }, 60),
            createRowWithPrefixAndSuffix("Caps Lock", new String[] { "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'" }, "Enter", 60),
            createRowWithShifts(new String[] { "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/" }, 60),
            createSpaceRow()
        );

        keyboard.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");
        keyboard.setAlignment(Pos.CENTER);

        Scene scene = new Scene(keyboard);
        configureKeyEvents(stage, scene);

        stage.setTitle("KeyBuddy");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(true);
        stage.show();
    }

    private void initializeShiftMap() {
        shiftMap.put(KeyCode.DIGIT1, "!");
        shiftMap.put(KeyCode.DIGIT2, "@");
        shiftMap.put(KeyCode.DIGIT3, "#");
        shiftMap.put(KeyCode.DIGIT4, "$");
        shiftMap.put(KeyCode.DIGIT5, "%");
        shiftMap.put(KeyCode.DIGIT6, "^");
        shiftMap.put(KeyCode.DIGIT7, "&");
        shiftMap.put(KeyCode.DIGIT8, "*");
        shiftMap.put(KeyCode.DIGIT9, "(");
        shiftMap.put(KeyCode.DIGIT0, ")");
        shiftMap.put(KeyCode.MINUS, "_");
        shiftMap.put(KeyCode.EQUALS, "+");
        shiftMap.put(KeyCode.BACK_SLASH, "|");
        shiftMap.put(KeyCode.OPEN_BRACKET, "{");
        shiftMap.put(KeyCode.CLOSE_BRACKET, "}");
        shiftMap.put(KeyCode.SEMICOLON, ":");
        shiftMap.put(KeyCode.QUOTE, "\"");
        shiftMap.put(KeyCode.COMMA, "<");
        shiftMap.put(KeyCode.PERIOD, ">");
        shiftMap.put(KeyCode.SLASH, "?");
        shiftMap.put(KeyCode.BACK_QUOTE, "~");
    }

    private Button createKey(String label, String color) {
        Button key = new Button(label);
        key.setPrefSize(40, 40);
        key.setFocusTraversable(false);

        String indexTabStyle = "";
        if (label.equalsIgnoreCase("F") || label.equalsIgnoreCase("J")) {
            indexTabStyle = Index_Tab_Style;
        }
        key.setStyle("-fx-background-color: " + color + "; -fx-text-fill: black;" + indexTabStyle);

        keyMap.put(label.toUpperCase(), key);
        return key;
    }

    private HBox createRow(String[] keys, double specialKeyWidth) {
        HBox row = new HBox(5);
        for (String key : keys) {
            Button btn = createKey(key, getColorForKey(key));
            if (key.equals("Delete"))
                btn.setPrefWidth(specialKeyWidth);
            row.getChildren().add(btn);
        }
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private HBox createRowWithPrefix(String prefix, String[] keys, double prefixWidth) {
        HBox row = new HBox(5);
        Button prefixKey = createKey(prefix, getColorForKey(prefix));
        prefixKey.setPrefWidth(prefixWidth);
        row.getChildren().add(prefixKey);
        for (String key : keys)
            row.getChildren().add(createKey(key.toLowerCase(), getColorForKey(key)));
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private HBox createRowWithPrefixAndSuffix(String prefix, String[] keys, String suffix, double specialWidth) {
        HBox row = new HBox(5);
        Button prefixKey = createKey(prefix, getColorForKey(prefix));
        prefixKey.setPrefWidth(specialWidth);
        row.getChildren().add(prefixKey);
        for (String key : keys)
            row.getChildren().add(createKey(key.toLowerCase(), getColorForKey(key)));
        Button suffixKey = createKey(suffix, getColorForKey(suffix));
        suffixKey.setPrefWidth(specialWidth);
        row.getChildren().add(suffixKey);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private HBox createRowWithShifts(String[] keys, double shiftWidth) {
        HBox row = new HBox(5);
        Button leftShift = createKey("Shift", getColorForKey("Shift"));
        leftShift.setPrefWidth(shiftWidth);
        Button rightShift = createKey("Shift", getColorForKey("Shift"));
        rightShift.setPrefWidth(shiftWidth);
        row.getChildren().add(leftShift);
        for (String key : keys)
            row.getChildren().add(createKey(key.toLowerCase(), getColorForKey(key)));
        row.getChildren().add(rightShift);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private HBox createSpaceRow() {
        HBox row = new HBox();
        Button spaceKey = createKey("Space", getColorForKey("Space"));
        spaceKey.setPrefWidth(300);
        row.getChildren().add(spaceKey);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private HBox createControlRow(Stage stage) {
        CheckBox topToggle = new CheckBox("Always On Top");
        topToggle.setSelected(true);
        topToggle.setOnAction(e -> stage.setAlwaysOnTop(topToggle.isSelected()));

        Slider transparency = new Slider(0.3, 1.0, 1.0);
        transparency.setShowTickLabels(true);
        transparency.setShowTickMarks(true);
        transparency.valueProperty().addListener((obs, oldVal, newVal) -> stage.setOpacity(newVal.doubleValue()));

        HBox controlRow = new HBox(10, topToggle, transparency);
        controlRow.setAlignment(Pos.CENTER);
        return controlRow;
    }

    private void configureKeyEvents(Stage stage, Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            shiftDown = event.isShiftDown();
            if (code == KeyCode.CAPS) {
                capsLockDown = !capsLockDown;
            }

            String keyText = resolveKeyText(code, event.getText());
            Button key = keyMap.getOrDefault(keyText.toUpperCase(), null);
            if (key != null)
                highlightKey(keyText.toUpperCase(), "lightgreen");

            updateLetterCase();
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            shiftDown = event.isShiftDown();

            if (code == KeyCode.SHIFT) {
                shiftDown = false;
                updateLetterCase();
            }

            resetKeyStyle("Caps Lock", getColorForKey("Caps Lock"));

            String keyText = resolveKeyText(code, event.getText());
            Button key = keyMap.getOrDefault(keyText.toUpperCase(), null);
            if (key != null) {
                resetKeyStyle(keyText.toUpperCase(), getColorForKey(keyText));
            }
        });
    }

    private void highlightKey(String keyText, String color) {
        Button key = keyMap.get(keyText.toUpperCase());
        if (key != null) {
            String extraStyle = "";
            if (Index_Tabs.contains(keyText.toUpperCase())) {
                extraStyle = Index_Tab_Style;
            }
            key.setStyle("-fx-background-color: " + color + "; -fx-text-fill: black;" + extraStyle);
        }
    }

    private void resetKeyStyle(String keyText, String color) {
        Button key = keyMap.get(keyText.toUpperCase());
        if (key != null) {
            String extraStyle = "";
            if (Index_Tabs.contains(keyText.toUpperCase())) {
                extraStyle = Index_Tab_Style;
            }
            key.setStyle("-fx-background-color: " + color + "; -fx-text-fill: black;" + extraStyle);
        }
    }

    private String resolveKeyText(KeyCode code, String rawText) {
        if (shiftDown && shiftMap.containsKey(code))
            return shiftMap.get(code);
        switch (code) {
            case SPACE:
                return "Space";
            case ENTER:
                return "Enter";
            case TAB:
                return "Tab";
            case CAPS:
                return "Caps Lock";
            case BACK_SPACE:
                return "Delete";
            case SHIFT:
                return "Shift";
            default:
                return rawText;
        }
    }

    private void updateLetterCase() {
        for (Map.Entry<String, Button> entry : keyMap.entrySet()) {
            String key = entry.getKey();
            Button btn = entry.getValue();
            if (key.length() == 1 && Character.isLetter(key.charAt(0))) {
                btn.setText((shiftDown ^ capsLockDown) ? key.toUpperCase() : key.toLowerCase());
            }
        }
    }

    private String getColorForKey(String key) {
        key = key.toUpperCase(); 
        if ("QAZP;/[]\\'10-=".contains(key)) return "#8C7891";
        if ("SHIFT".equals(key)) return "#8C7891";
        if ("ENTER".equals(key)) return "#8C7891";
        if ("TAB".equals(key)) return "#8C7891";
        if ("CAPS LOCK".equals(key)) return "#8C7891";
        if ("DELETE".equals(key)) return "#8C7891";
        if ("2WSX9OL.".contains(key)) return "#A7AADD";
        if ("3EDC8IK,".contains(key)) return "#B8E4E1";
        if ("45RFVTGB".contains(key)) return "#6CBCCE";
        if ("67YHNUJM".contains(key)) return "#ADD8E6";
        if ("SPACE".equals(key)) return "#D3D3D3";
        return "#FFFFFF";
    }

    public static void main(String[] args) {
        launch(args);
    }
}