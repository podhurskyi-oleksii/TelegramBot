package ua.javacoders.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

import static ua.javacoders.utils.FilesPath.USERS;

/**
 * The UsersStorage class is responsible for saving and loading User objects
 * to and from a file. It uses Gson to serialize and deserialize the data.
 */
public class UsersStorage {
    private final Gson gson;
    private static final Logger LOG = LogManager.getLogger(UsersStorage.class);

    /**
     * Constructs a UsersStorage instance and initializes the Gson object.
     */
    public UsersStorage() {
        gson = new Gson();
    }

    /**
     * Saves the given ConcurrentHashMap of users to a file.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Open a FileWriter for the file</li>
     *     <li>Serialize the ConcurrentHashMap of users to a JSON string using Gson</li>
     *     <li>Write the JSON string to the file using the FileWriter</li>
     *     <li>Close the FileWriter</li>
     * </ul>
     *
     * @param users the ConcurrentHashMap of users to save.
     */
    public void saveUsers(ConcurrentHashMap<Long, User> users) {
        try (FileWriter writer = new FileWriter(USERS.getFilePath())) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            LOG.error("Error while saving users to file: {}", USERS.getFilePath(), e);
        }
    }

    /**
     * Loads the ConcurrentHashMap of users from a file.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Check if the file is not empty</li>
     *     <li>Open a FileReader for the file</li>
     *     <li>Deserialize the JSON string from the file into a ConcurrentHashMap of users using Gson</li>
     *     <li>Close the FileReader</li>
     *     <li>Return the deserialized ConcurrentHashMap of users</li>
     *     <li>If an error occurs, it is logged</li>
     * </ul>
     *
     * @return the ConcurrentHashMap of users, or an empty ConcurrentHashMap if the file is empty or an error occurs.
     */
    public ConcurrentHashMap<Long, User> loadUsers() {
        File file = new File(USERS.getFilePath());
        if (file.length() != 0) {
            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<ConcurrentHashMap<Long, User>>() {
                }.getType();
                return gson.fromJson(reader, type);
            } catch (IOException e) {
                LOG.error("Error while loading users from file: {}", USERS.getFilePath(), e);
            }
        }
        return new ConcurrentHashMap<>();
    }
}
