package ua.procamp;

public class Functions {
    /**
     * A static factory method that creates an integer function map with basic functions:
     * - abs (absolute value)
     * - sgn (signum function)
     * - increment
     * - decrement
     * - square
     *
     * @return an instance of {@link FunctionMap} that contains all listed functions
     */
    public static FunctionMap<Integer, Integer> intFunctionMap() {
        FunctionMap<Integer, Integer> intFunctionMap = new FunctionMap<>();

        intFunctionMap.addFunction("abs", v -> v > 0 ? v : -v);
        intFunctionMap.addFunction("sgn", v -> {
            if (v == 0) return 0;
            return v > 0 ? 1 : -1;
        });
        intFunctionMap.addFunction("increment", v -> v + 1);
        intFunctionMap.addFunction("decrement", v -> v - 1);
        intFunctionMap.addFunction("square", v -> v * v);
        // todo: add simple functions to the function map (abs, sng, increment, decrement, square)

        return intFunctionMap;
    }
}
