package cz.mendelu;

import java.util.List;

public interface FeeService {

    List<Fee> getAllFees();

    Fee getFeeById(int feeId);

    Fee saveFee(Fee fee);

    void updateFee(Fee fee);

    void deleteFee(int feeId);
}