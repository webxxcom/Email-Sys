package com.email.sys.cell.factories;

import com.email.sys.configurators.ConfigKey;
import com.email.sys.configurators.ConfigStorage;
import com.email.sys.entities.User;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class UsersCellFactory implements Callback<ListView<User>, ListCell<User>> {

    private final ConfigStorage configStorage;

    public UsersCellFactory(ConfigStorage configStorage) {
        this.configStorage = configStorage;
    }

    @Override
    public ListCell<User> call(ListView<User> userListView) {
        return new ListCell<>(){
            @Override
            protected void updateItem(User user, boolean b) {
                super.updateItem(user, b);
                if(b || user == null){
                    setGraphic(null);
                }else{
                    setGraphic(getCellLayout(user));
                    setActions(user);
                }
            }

            private void setActions(User user) {
                setOnMouseClicked(evt->{
                    if(evt.getClickCount() == 2)
                        configStorage.add(ConfigKey.USER, user);
                });
            }

            private Node getCellLayout(User user) {
                return new Label(user.getEmail());
            }
        };
    }
}
