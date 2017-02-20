package ua.org.smartjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ua.org.smartjava.domain.primaryKey.EmpAuto;
import ua.org.smartjava.domain.primaryKey.EmpSequence;
import ua.org.smartjava.domain.primaryKey.EmpTable;
import ua.org.smartjava.repositories.EmpAutoRepository;
import ua.org.smartjava.repositories.EmpSequenceRepository;
import ua.org.smartjava.repositories.EmpTableRepository;

@Service
public class TestUtils {

    private HashMap<Class, JpaRepository> repositories;
    private HashMap<Class, IntFunction> intFunctions;

    @Autowired
    public TestUtils(EmpAutoRepository empAutoRepository,
                     EmpTableRepository empTableRepository,
                     EmpSequenceRepository empSequenceRepository) {
        repositories = new HashMap<>();
        intFunctions = new HashMap<>();

        repositories.put(EmpAuto.class, empAutoRepository);
        intFunctions.put(EmpAuto.class, i -> EmpAuto.builder().name("NAME_" + i).build());

        repositories.put(EmpTable.class, empTableRepository);
        intFunctions.put(EmpTable.class, i -> new EmpTable());

        repositories.put(EmpSequence.class, empSequenceRepository);
        intFunctions.put(EmpSequence.class, i -> new EmpSequence());
    }

    public void preparePersistenceClasses(Class entity, int count) throws IllegalAccessException, InstantiationException {
        persistEntity(intFunctions.get(entity), IntStream.range(0, count))
                .forEach(repositories.get(entity)::save);
    }

    private Stream<EmpAuto> persistEntity(IntFunction<EmpAuto> entityIntFunction, IntStream intStream) {
        return intStream.mapToObj(entityIntFunction);
    }

}