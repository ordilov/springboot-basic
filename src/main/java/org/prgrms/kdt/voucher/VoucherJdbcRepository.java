package org.prgrms.kdt.voucher;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:48 오후
 */

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var name = resultSet.getString("name");
        var discount = resultSet.getLong("discount");
        var voucherType = resultSet.getString("voucher_type");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Voucher(voucherId, name, discount, VoucherType.valueOf(voucherType), createdAt);
    };

    @Override
    public int insert(Voucher voucher) {
        return jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, name, voucher_type, discount, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getName(),
                voucher.getVoucherType().name(),
                voucher.getDiscount(),
                Timestamp.valueOf(voucher.getCreatedAt()));
    }

    @Override
    public int update(Voucher voucher) {
        return jdbcTemplate.update("UPDATE vouchers SET name = ? WHERE voucher_id = UUID_TO_BIN(?)",
                voucher.getName(),
                voucher.getVoucherId().toString().getBytes());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate
                    .queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                            voucherRowMapper,
                            voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result ", e);
            return Optional.empty();
        }

    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return jdbcTemplate
                .query(
                        "SELECT * FROM vouchers WHERE voucher_type = ?",
                        voucherRowMapper,
                        voucherType.name());
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return jdbcTemplate.query("select * from vouchers "
                                  + "LEFT JOIN wallets "
                                  + "ON wallets.customer_id = UUID_TO_BIN(?) "
                                  + "WHERE wallets.voucher_id = vouchers.voucher_id",
                voucherRowMapper,
                customerId.toString().getBytes());
    }

    @Override
    public int deleteById(UUID voucherId) {
        return jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                voucherId.toString().getBytes());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers");
    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM vouchers", Integer.class);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM  vouchers", voucherRowMapper);
    }

    @Override
    public List<Voucher> findByPeriodByCreatedAt(LocalDate beforeDate, LocalDate afterDate) {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE created_at >= (?) AND created_at <= (?)",
                voucherRowMapper,
                beforeDate, afterDate);
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}