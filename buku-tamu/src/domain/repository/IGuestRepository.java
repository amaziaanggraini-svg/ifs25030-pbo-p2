package domain.repository;

import domain.entity.Guest;
import java.util.List;

public interface IGuestRepository {
    Guest save(String name, String purpose);
    List<Guest> findAll();
    List<Guest> findByKeyword(String keyword);
    boolean deleteById(String id);
}