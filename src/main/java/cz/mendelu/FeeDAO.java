package cz.mendelu;

import java.util.List;

public interface FeeDAO {

    // Retrieve all fees from database
    List<Fee> getAll();

    // Retrieve one fee by ID
    Fee getById(int feeId);

    // Insert a new fee into database and return it with generated ID
    Fee save(Fee fee);

    // Update an existing fee in database
    void update(Fee fee);

    // Delete a fee from database
    void delete(int feeId);
}