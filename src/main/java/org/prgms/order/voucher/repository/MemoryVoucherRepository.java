package org.prgms.order.voucher.repository;

import org.prgms.order.voucher.entity.Voucher;
import org.prgms.order.voucher.entity.VoucherIndexType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"dev"})
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByType(VoucherIndexType Type) {
        return null;
    }


    @Override
    public List<Voucher> findByTypeAmount(VoucherIndexType Type, long amount) {
        return null;
    }

    @Override
    public List<Voucher> findAvailables() {
        return null;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {

        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void updateExpiryDate(UUID voucherId, LocalDateTime withNano) {
        return;
    }

}