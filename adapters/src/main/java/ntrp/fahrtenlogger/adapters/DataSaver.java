package ntrp.fahrtenlogger.adapters;

import ntrp.fahrtenlogger.domain.RepositoryInterface;

public interface DataSaver {
    boolean saveAllRepositories();
    boolean saveRepository(RepositoryInterface repository);
}
