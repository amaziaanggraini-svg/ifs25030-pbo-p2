package adapter.repository;

import domain.entity.Activity;
import domain.repository.IActivityRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementasi repository menggunakan penyimpanan in-memory berbasis {@link List}.
 * Berada di layer adapter, mengimplementasikan port dari domain.
 */
public class ActivityRepository implements IActivityRepository {
    /** Penyimpanan data kegiatan di memori. */
    private final List<Activity> data = new ArrayList<>();

    /** Penghitung ID otomatis, bertambah setiap kali kegiatan baru disimpan. */
    private int idCounter = 0;

    @Override
    public List<Activity> findAll() {
        // Salinan defensif agar data internal tidak bisa diubah dari luar
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Activity> findById(int id) {
        return data.stream()
                .filter(activity -> activity.getId() == id)
                .findFirst();
    }

    @Override
    public Activity save(String title, String day, String time) {
        Activity activity = new Activity(idCounter + 1, title, day, time);
        idCounter++;
        data.add(activity);
        return activity;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(activity -> activity.getId() == id);
    }

    @Override
    public void update(Activity activity) {
        // Entity immutable: elemen lama harus diganti dengan objek baru yang ber-ID sama.
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == activity.getId()) {
                data.set(i, activity);
                return;
            }
        }
    }
}