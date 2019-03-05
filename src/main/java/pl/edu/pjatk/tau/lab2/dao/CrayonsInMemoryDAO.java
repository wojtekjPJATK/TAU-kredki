package pl.edu.pjatk.tau.lab2.dao;

import pl.edu.pjatk.tau.lab2.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CrayonsInMemoryDAO implements DAO<Crayon> {
    protected Map<Long,Crayon> crayons;

    @Override
    public Optional<Crayon> get(Long id) {
        return Optional.ofNullable(crayons.get(id));
    }

    @Override
    public List<Crayon> getAll() {
        return new ArrayList<Crayon>(crayons.values());
    }

    @Override
    public void save(Crayon o) {
        if (crayons.containsKey(o.getId()))
            throw new IllegalArgumentException("Key already exist");
        crayons.put(o.getId(), o);
    }

    @Override
    public void update(Crayon o) throws IllegalArgumentException {
        if (!crayons.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does not exist");
        crayons.put(o.getId(), o);
    }

    @Override
    public void delete(Crayon o) {
        crayons.remove(o.getId());
    }
}