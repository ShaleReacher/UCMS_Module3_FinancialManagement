package cz.mendelu;

import java.util.List;

public class FeeServiceImpl implements FeeService {

    private FeeDAO feeDAO = new FeeDAOImpl();

    @Override
    public List<Fee> getAllFees() {
        return feeDAO.getAll();
    }

    @Override
    public Fee getFeeById(int feeId) {
        return feeDAO.getById(feeId);
    }

    @Override
    public Fee saveFee(Fee fee) {
        return feeDAO.save(fee);
    }

    @Override
    public void updateFee(Fee fee) {
        feeDAO.update(fee);
    }

    @Override
    public void deleteFee(int feeId) {
        feeDAO.delete(feeId);
    }
}