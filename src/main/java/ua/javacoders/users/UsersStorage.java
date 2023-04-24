package ua.javacoders.users;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

public class UsersStorage {
    private final String USERS_FILE = "C:\\workspace\\TelegramBot\\src\\main\\resources\\users.json";
    private Gson gson;

    public UsersStorage() {
        gson = new Gson();
    }

    public void saveUsers(ConcurrentHashMap<Long, User> users) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConcurrentHashMap<Long, User> loadUsers() {
        File file = new File(USERS_FILE);
        if (file.length() != 0) {
            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<ConcurrentHashMap<Long, User>>() {
                }.getType();
                return gson.fromJson(reader, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ConcurrentHashMap<>();
    }
}
