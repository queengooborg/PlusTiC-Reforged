package queengooborg.plustic.modules;

import java.util.*;

public interface IModule {
	Set<IModule> modules = new LinkedHashSet<>();

	default void init() {
	}

	default void init2() {
	}

	default void init3() {
	}
}
