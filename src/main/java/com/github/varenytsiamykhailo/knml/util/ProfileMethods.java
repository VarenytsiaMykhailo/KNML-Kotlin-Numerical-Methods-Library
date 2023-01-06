package com.github.varenytsiamykhailo.knml.util;

import com.github.varenytsiamykhailo.knml.integralmethods.RectangleMethod;
import com.github.varenytsiamykhailo.knml.integralmethods.SimpsonMethod;
import com.github.varenytsiamykhailo.knml.integralmethods.TrapezoidMethod;
import com.github.varenytsiamykhailo.knml.systemsolvingmethods.GaussMethod;
import com.github.varenytsiamykhailo.knml.systemsolvingmethods.JacobiMethod;
import com.github.varenytsiamykhailo.knml.systemsolvingmethods.SeidelMethod;
import com.github.varenytsiamykhailo.knml.systemsolvingmethods.ThomasMethod;
import com.github.varenytsiamykhailo.knml.util.results.DoubleResultWithStatus;
import com.github.varenytsiamykhailo.knml.util.results.VectorResultWithStatus;
import org.jfree.data.xy.XYSeries;

import com.github.varenytsiamykhailo.knml.util.Profiler.Timeable;

import java.util.concurrent.Future;

import kotlin.Pair;

public class ProfileMethods {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //profileJacobiMethod();
        //profileThomasMethod();
        //profileGaussMethod();
        //profileSeidelMethod();
        //profileRectangleMethod();
        //profileTrapezoidMethod();
        //profileSimpsonMethod();
        //profileArrayListAddBeginning();
        //profileLinkedListAddBeginning();
        //profileLinkedListAddEnd();

        //profileMatrixAdd();
        //profileMatrixMultiplyNumber();
        //profileMatrixMultiplyVector();
        //profileMatrixMultiplyMatrix();

        //profileVectorAdd();
        //profileMatrixNorm();
        //profileVectorNorm();
        //profileVectorMultiplyNumber();
        //profileVectorMultiplyVector();
        //profileMatrixTranspose();

        //profileMatrixDeterminantWithGauss();
        //profileMatrixDeterminant();
        //profileMatrixAdjoint();
        //profileMatrixInvertible();

        //profileGaussMethodWithPivotingByColumn();
        //profileGaussMethodWithPivotingByRow();
        //profileGaussMethodWithPivotingComplete();

        //profileLUDecomposition();
        profileQRDecomposition();

        //profileMatrixMultiplyWithStrassen();
        //profileMatrixMultiplyWithStrassenMultithreading();
    }

    public static void profileJacobiMethod() {
        Timeable timeable = new Timeable() {
            JacobiMethod jacobiMethod;

            public void setup(int n) {
                jacobiMethod = new JacobiMethod();
            }

            public void timeMe(int n) {
                Double[][] matrix = new Double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == j) {
                            matrix[i][j] = (i + 1) * (j + 1) * 100.0;
                        } else {
                            if (j % 3 == 0) {
                                matrix[i][j] = (double) (i * j) / 100;
                            } else if (j % 5 == 0) {
                                matrix[i][j] = 0 + (double) i / 100;
                            } else {
                                matrix[i][j] = -(double) (i * j) / 100;
                            }
                        }
                    }
                }
                StringBuilder formedMatrixString = new StringBuilder();
                for (Double[] doubles : matrix) {
                    formedMatrixString.append("\n");
                    for (int j = 0; j < matrix.length; j++) {
                        formedMatrixString.append(doubles[j]).append("\t");
                    }
                    formedMatrixString.append("\n");
                }
                System.out.println("formed matrix = " + formedMatrixString);

                Double[] vector = new Double[n];
                for (int i = 0; i < vector.length; i++) {
                    if (i % 2 == 0) {
                        vector[i] = i * 100. + 1;
                    } else if (i % 3 == 0) {
                        vector[i] = i * 100. + i + 1;
                    } else {
                        vector[i] = i * 100. - i + 1;
                    }
                }
                String formedVectorString = "\n";
                for (Double aDouble : vector) {
                    formedVectorString += aDouble + "\t";
                }
                formedVectorString += "\n";
                System.out.println("formed vector = " + formedVectorString);

                VectorResultWithStatus vectorResultWithStatus = jacobiMethod.solveSystemByJacobiMethod(matrix, vector, null, 0.001, false);
                if (!vectorResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(vectorResultWithStatus.getErrorException());
                } else {
                    System.out.println(vectorResultWithStatus.getVectorResult());
                }
            }
        };
        int startN = 8;
        int endMillis = 300;

        runProfiler("Jacobi method profile", timeable, startN, endMillis);
    }

    public static void profileSeidelMethod() {
        Timeable timeable = new Timeable() {
            SeidelMethod seidelMethod;

            public void setup(int n) {
                seidelMethod = new SeidelMethod();
            }

            public void timeMe(int n) {
                Double[][] matrix = new Double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == j) {
                            matrix[i][j] = (i + 1) * (j + 1) * 100.0;
                        } else {
                            if (j % 3 == 0) {
                                matrix[i][j] = (double) (i * j) / 100;
                            } else if (j % 5 == 0) {
                                matrix[i][j] = 0 + (double) i / 100;
                            } else {
                                matrix[i][j] = -(double) (i * j) / 100;
                            }
                        }
                    }
                }
                StringBuilder formedMatrixString = new StringBuilder();
                for (Double[] doubles : matrix) {
                    formedMatrixString.append("\n");
                    for (int j = 0; j < matrix.length; j++) {
                        formedMatrixString.append(doubles[j]).append("\t");
                    }
                    formedMatrixString.append("\n");
                }
                System.out.println("formed matrix = " + formedMatrixString);

                Double[] vector = new Double[n];
                for (int i = 0; i < vector.length; i++) {
                    if (i % 2 == 0) {
                        vector[i] = i * 100. + 1;
                    } else if (i % 3 == 0) {
                        vector[i] = i * 100. + i + 1;
                    } else {
                        vector[i] = i * 100. - i + 1;
                    }
                }
                StringBuilder formedVectorString = new StringBuilder("\n");
                for (Double aDouble : vector) {
                    formedVectorString.append(aDouble).append("\t");
                }
                formedVectorString.append("\n");
                System.out.println("formed vector = " + formedVectorString);

                VectorResultWithStatus vectorResultWithStatus = seidelMethod.solveSystemBySeidelMethod(matrix, vector, null, 0.001, false);
                if (!vectorResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(vectorResultWithStatus.getErrorException());
                } else {
                    System.out.println(vectorResultWithStatus.getVectorResult());
                }
            }
        };
        int startN = 8;
        int endMillis = 300;

        runProfiler("Seidel method profile", timeable, startN, endMillis);
    }

    public static void profileGaussMethod() {
        Timeable timeable = new Timeable() {
            GaussMethod gaussMethod;
            Double[][] matrix;
            Double[] vector;

            public void setup(int n) {
                gaussMethod = new GaussMethod();

                matrix = new Double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == j) {
                            matrix[i][j] = (i + 1) * (j + 1) * 100.0;
                        } else {
                            if (j % 3 == 0) {
                                matrix[i][j] = (double) (i * j) / 100;
                            } else if (j % 5 == 0) {
                                matrix[i][j] = 0 + (double) i / 100;
                            } else {
                                matrix[i][j] = -(double) (i * j) / 100;
                            }
                        }
                    }
                }
                StringBuilder formedMatrixString = new StringBuilder();
                for (Double[] doubles : matrix) {
                    formedMatrixString.append("\n");
                    for (int j = 0; j < matrix.length; j++) {
                        formedMatrixString.append(doubles[j]).append("\t");
                    }
                    formedMatrixString.append("\n");
                }
                System.out.println("formed matrix = " + formedMatrixString);

                vector = new Double[n];
                for (int i = 0; i < vector.length; i++) {
                    if (i % 2 == 0) {
                        vector[i] = i * 100. + 1;
                    } else if (i % 3 == 0) {
                        vector[i] = i * 100. + i + 1;
                    } else {
                        vector[i] = i * 100. - i + 1;
                    }
                }
                StringBuilder formedVectorString = new StringBuilder("\n");
                for (Double aDouble : vector) {
                    formedVectorString.append(aDouble).append("\t");
                }
                formedVectorString.append("\n");
                System.out.println("formed vector = " + formedVectorString);
            }

            public void timeMe(int n) {
                VectorResultWithStatus vectorResultWithStatus =
                        gaussMethod.solveSystemByGaussClassicMethod(matrix, vector, false);
                if (!vectorResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(vectorResultWithStatus.getErrorException());
                } else {
                    System.out.println(vectorResultWithStatus.getVectorResult());
                }
            }
        };
        int startN = 8;
        int endMillis = 300;

        runProfiler("Gauss method profile", timeable, startN, endMillis);
    }

    public static void profileGaussMethodWithPivotingByColumn() {
        Timeable timeable = new Timeable() {
            GaussMethod gaussMethod;
            Double[][] matrix;
            Double[] vector;

            public void setup(int n) {
                gaussMethod = new GaussMethod();

                matrix = new Double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == j) {
                            matrix[i][j] = (i + 1) * (j + 1) * 100.0;
                        } else {
                            if (j % 3 == 0) {
                                matrix[i][j] = (double) (i * j) / 100;
                            } else if (j % 5 == 0) {
                                matrix[i][j] = 0 + (double) i / 100;
                            } else {
                                matrix[i][j] = -(double) (i * j) / 100;
                            }
                        }
                    }
                }
                StringBuilder formedMatrixString = new StringBuilder();
                for (Double[] doubles : matrix) {
                    formedMatrixString.append("\n");
                    for (int j = 0; j < matrix.length; j++) {
                        formedMatrixString.append(doubles[j]).append("\t");
                    }
                    formedMatrixString.append("\n");
                }
                System.out.println("formed matrix = " + formedMatrixString);

                vector = new Double[n];
                for (int i = 0; i < vector.length; i++) {
                    if (i % 2 == 0) {
                        vector[i] = i * 100. + 1;
                    } else if (i % 3 == 0) {
                        vector[i] = i * 100. + i + 1;
                    } else {
                        vector[i] = i * 100. - i + 1;
                    }
                }
                StringBuilder formedVectorString = new StringBuilder("\n");
                for (Double aDouble : vector) {
                    formedVectorString.append(aDouble).append("\t");
                }
                formedVectorString.append("\n");
                System.out.println("formed vector = " + formedVectorString);
            }

            public void timeMe(int n) {
                VectorResultWithStatus vectorResultWithStatus =
                        gaussMethod.solveSystemByGaussMethodWithPivoting(matrix, vector, false, GaussMethod.PivotingStrategy.PartialByColumn);
                if (!vectorResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(vectorResultWithStatus.getErrorException());
                } else {
                    System.out.println(vectorResultWithStatus.getVectorResult());
                }
            }
        };
        int startN = 8;
        int endMillis = 300;

        runProfiler("Gauss method pivoting by column profile", timeable, startN, endMillis);
    }

    public static void profileGaussMethodWithPivotingByRow() {
        Timeable timeable = new Timeable() {
            GaussMethod gaussMethod;
            Double[][] matrix;
            Double[] vector;

            public void setup(int n) {
                gaussMethod = new GaussMethod();

                matrix = new Double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == j) {
                            matrix[i][j] = (i + 1) * (j + 1) * 100.0;
                        } else {
                            if (j % 3 == 0) {
                                matrix[i][j] = (double) (i * j) / 100;
                            } else if (j % 5 == 0) {
                                matrix[i][j] = 0 + (double) i / 100;
                            } else {
                                matrix[i][j] = -(double) (i * j) / 100;
                            }
                        }
                    }
                }
                StringBuilder formedMatrixString = new StringBuilder();
                for (Double[] doubles : matrix) {
                    formedMatrixString.append("\n");
                    for (int j = 0; j < matrix.length; j++) {
                        formedMatrixString.append(doubles[j]).append("\t");
                    }
                    formedMatrixString.append("\n");
                }
                System.out.println("formed matrix = " + formedMatrixString);

                vector = new Double[n];
                for (int i = 0; i < vector.length; i++) {
                    if (i % 2 == 0) {
                        vector[i] = i * 100. + 1;
                    } else if (i % 3 == 0) {
                        vector[i] = i * 100. + i + 1;
                    } else {
                        vector[i] = i * 100. - i + 1;
                    }
                }
                StringBuilder formedVectorString = new StringBuilder("\n");
                for (Double aDouble : vector) {
                    formedVectorString.append(aDouble).append("\t");
                }
                formedVectorString.append("\n");
                System.out.println("formed vector = " + formedVectorString);
            }

            public void timeMe(int n) {
                VectorResultWithStatus vectorResultWithStatus =
                        gaussMethod.solveSystemByGaussMethodWithPivoting(matrix, vector, false, GaussMethod.PivotingStrategy.PartialByRow);
                if (!vectorResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(vectorResultWithStatus.getErrorException());
                } else {
                    System.out.println(vectorResultWithStatus.getVectorResult());
                }
            }
        };
        int startN = 8;
        int endMillis = 300;

        runProfiler("Gauss method pivoting by row profile", timeable, startN, endMillis);
    }

    public static void profileGaussMethodWithPivotingComplete() {
        Timeable timeable = new Timeable() {
            GaussMethod gaussMethod;
            Double[][] matrix;
            Double[] vector;

            public void setup(int n) {
                gaussMethod = new GaussMethod();

                matrix = new Double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == j) {
                            matrix[i][j] = (i + 1) * (j + 1) * 100.0;
                        } else {
                            if (j % 3 == 0) {
                                matrix[i][j] = (double) (i * j) / 100;
                            } else if (j % 5 == 0) {
                                matrix[i][j] = 0 + (double) i / 100;
                            } else {
                                matrix[i][j] = -(double) (i * j) / 100;
                            }
                        }
                    }
                }
                StringBuilder formedMatrixString = new StringBuilder();
                for (Double[] doubles : matrix) {
                    formedMatrixString.append("\n");
                    for (int j = 0; j < matrix.length; j++) {
                        formedMatrixString.append(doubles[j]).append("\t");
                    }
                    formedMatrixString.append("\n");
                }
                System.out.println("formed matrix = " + formedMatrixString);

                vector = new Double[n];
                for (int i = 0; i < vector.length; i++) {
                    if (i % 2 == 0) {
                        vector[i] = i * 100. + 1;
                    } else if (i % 3 == 0) {
                        vector[i] = i * 100. + i + 1;
                    } else {
                        vector[i] = i * 100. - i + 1;
                    }
                }
                StringBuilder formedVectorString = new StringBuilder("\n");
                for (Double aDouble : vector) {
                    formedVectorString.append(aDouble).append("\t");
                }
                formedVectorString.append("\n");
                System.out.println("formed vector = " + formedVectorString);
            }

            public void timeMe(int n) {
                VectorResultWithStatus vectorResultWithStatus =
                        gaussMethod.solveSystemByGaussMethodWithPivoting(matrix, vector, false, GaussMethod.PivotingStrategy.Complete);
                if (!vectorResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(vectorResultWithStatus.getErrorException());
                } else {
                    System.out.println(vectorResultWithStatus.getVectorResult());
                }
            }
        };
        int startN = 8;
        int endMillis = 300;

        runProfiler("Gauss method complete pivoting profile", timeable, startN, endMillis);
    }

    public static void profileThomasMethod() {
        Timeable timeable = new Timeable() {
            ThomasMethod thomasMethod;

            Double[][] matrix;
            Double[] vector;

            public void setup(int n) {
                thomasMethod = new ThomasMethod();

                matrix = new Double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == 0) {
                            if ((j == 0) || (j == 1)) {
                                matrix[i][j] = (double) (j % 7) + (j % 2) + (j % 3) + 1.0;
                            } else {
                                matrix[i][j] = 0.0;
                            }
                        } else if (i == n - 1) {
                            if ((j == n - 2) || (j == n - 1)) {
                                matrix[i][j] = (double) (j % 8) + (j % 2) + (i % 3) * (j % 2) + 1.0;
                            } else {
                                matrix[i][j] = 0.0;
                            }
                        } else {
                            if ((j == i - 1) || (j == i) || (j == i + 1)) {
                                matrix[i][j] = (double) (j % 5) + (j % 2) + (i % 4) * (i % 3) + 1.0;
                            } else {
                                matrix[i][j] = 0.0;
                            }
                        }
                    }
                }
                /*
                String formedMatrixString = "";
                for (int i = 0; i < matrix.length; i++) {
                    formedMatrixString += "\n";
                    for (int j = 0; j < matrix.length; j++) {
                        formedMatrixString += matrix[i][j] + "\t";
                    }
                    formedMatrixString += "\n";
                }
                System.out.println("formed matrix = " + formedMatrixString);
*/
                vector = new Double[n];
                for (int i = 0; i < vector.length; i++) {
                    if (i % 2 == 0) {
                        vector[i] = (i % 4) * 10. + 1.0;
                    } else if (i % 3 == 0) {
                        vector[i] = (i % 4) * 10. + (i % 4) + 1.0;
                    } else {
                        vector[i] = (i % 4) * 10. - (i % 4) + 1.0;
                    }
                }

                String formedVectorString = "\n";
                for (int i = 0; i < vector.length; i++) {
                    formedVectorString += vector[i] + "\t";
                }
                formedVectorString += "\n";
                System.out.println("formed vector = " + formedVectorString);

            }

            public void timeMe(int n) {
                VectorResultWithStatus vectorResultWithStatus = thomasMethod.solveSystemByThomasMethod(matrix, vector, false, true);
                if (!vectorResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(vectorResultWithStatus.getErrorException());
                } else {
                    System.out.println(vectorResultWithStatus.getVectorResult());
                }
            }
        };
        int startN = 32;
        int endMillis = 100;

        runProfiler("Thomas method profile", timeable, startN, endMillis);
    }


    public static void profileRectangleMethod() {
        Timeable timeable = new Timeable() {
            RectangleMethod rectangleMethod;

            Double intervalStart = 0.0;
            Double intervalEnd;

            public void setup(int n) {
                rectangleMethod = new RectangleMethod();

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);

            }

            public void timeMe(int n) {
                DoubleResultWithStatus doubleResultWithStatus = rectangleMethod.solveIntegralByRectangleMethod(intervalStart, intervalEnd, 0.0001, false, (x) -> x);
                if (!doubleResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(doubleResultWithStatus.getErrorException());
                } else {
                    System.out.println(doubleResultWithStatus.getDoubleResult());
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Rectangle method profile", timeable, startN, endMillis);
    }


    public static void profileTrapezoidMethod() {
        Timeable timeable = new Timeable() {
            TrapezoidMethod trapezoidMethod;

            Double intervalStart = 0.0;
            Double intervalEnd;

            public void setup(int n) {
                trapezoidMethod = new TrapezoidMethod();

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);

            }

            public void timeMe(int n) {
                DoubleResultWithStatus doubleResultWithStatus = trapezoidMethod.solveIntegralByTrapezoidMethod(intervalStart, intervalEnd, 0.0001, false, (x) -> x);
                if (!doubleResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(doubleResultWithStatus.getErrorException());
                } else {
                    System.out.println(doubleResultWithStatus.getDoubleResult());
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Trapezoid method profile", timeable, startN, endMillis);
    }


    public static void profileSimpsonMethod() {
        Timeable timeable = new Timeable() {
            SimpsonMethod simpsonMethod;

            Double intervalStart = 0.0;
            Double intervalEnd;

            public void setup(int n) {
                simpsonMethod = new SimpsonMethod();

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);

            }

            public void timeMe(int n) {
                DoubleResultWithStatus doubleResultWithStatus = simpsonMethod.solveIntegralBySimpsonMethod(intervalStart, intervalEnd, 0.0001, false, (x) -> x);
                if (!doubleResultWithStatus.isSuccessful()) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(doubleResultWithStatus.getErrorException());
                } else {
                    System.out.println(doubleResultWithStatus.getDoubleResult());
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Simpson method profile", timeable, startN, endMillis);
    }

    public static void profileMatrixAdd() {
        Timeable timeable = new Timeable() {

            Double intervalStart = 0.0;
            Double intervalEnd;

            Matrix m1;
            Matrix m2;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);
                m2 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 2);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Matrix result = m1.add(m2);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Add matrix method profile", timeable, startN, endMillis);
    }

    public static void profileMatrixMultiplyNumber() {
        Timeable timeable = new Timeable() {

            Double intervalStart = 0.0;
            Double intervalEnd;

            Matrix m1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Matrix result = m1.multiply(n);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Add matrix multiply number profile", timeable, startN, endMillis);
    }

    public static void profileMatrixMultiplyVector() {
        Timeable timeable = new Timeable() {

            Double intervalStart = 0.0;
            Double intervalEnd;

            Matrix m1;
            Vector v1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);
                v1 = HelpfulFunctionsKt.getVectorWithRandomElements(n, 0, 15);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Vector result = m1.multiply(v1);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Add matrix multiply vector profile", timeable, startN, endMillis);
    }

    public static void profileMatrixMultiplyMatrix() {
        Timeable timeable = new Timeable() {

            Double intervalStart = 0.0;
            Double intervalEnd;

            Matrix m1;
            Matrix m2;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);
                m2 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Matrix result = m1.multiply(m2);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Add matrix multiply matrix profile", timeable, startN, endMillis);
    }

    public static void profileVectorAdd() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Vector m1;
            Vector m2;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getVectorWithRandomElements(n, 0, 15);
                m2 = HelpfulFunctionsKt.getVectorWithRandomElements(n, 0, 15);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Vector result = m1.add(m2);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Add vector method profile", timeable, startN, endMillis);
    }

    public static void profileVectorMultiplyNumber() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Vector m1;
            double number;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getVectorWithRandomElements(n, 0, 15);
                number = n;

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Vector result = m1.multiply(number);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Vector multiply number method profile", timeable, startN, endMillis);
    }

    public static void profileVectorMultiplyVector() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Vector m1;
            Vector m2;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getVectorWithRandomElements(n, 0, 15);
                m2 = HelpfulFunctionsKt.getVectorWithRandomElements(n, 0, 15);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Vector result = m1.multiply(m2);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Vector multiply vector method profile", timeable, startN, endMillis);
    }

    public static void profileVectorNorm() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Vector v1;

            public void setup(int n) {
                v1 = HelpfulFunctionsKt.getVectorWithRandomElements(n, 0, 15);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                System.out.println(v1.norm());
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Vector norm method profile", timeable, startN, endMillis);
    }

    public static void profileMatrixNorm() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Matrix m1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                double result = m1.norm();
                System.out.println(result);
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix norm method profile", timeable, startN, endMillis);
    }

    public static void profileMatrixTranspose() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Matrix m1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Matrix result = m1.transpose();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix transpose method profile", timeable, startN, endMillis);
    }

    public static void profileMatrixAdjoint() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Matrix m1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Matrix result = m1.adjoint();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix adjoint method profile", timeable, startN, endMillis);
    }

    public static void profileMatrixDeterminant() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Matrix m1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    double result = m1.determinant(n);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix determinant method profile", timeable, startN, endMillis);
    }

    public static void profileMatrixDeterminantWithGauss() {
        Timeable timeable = new Timeable() {

            Double intervalStart = 0.0;
            Double intervalEnd;

            Matrix m1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    double result = m1.determinantWithGauss();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix determinant method with Gauss profile", timeable, startN, endMillis);
    }

    public static void profileMatrixMultiplyWithStrassen() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            StrassenAlgorithm strassenAlgorithm;
            Matrix m1;
            Matrix m2;

            public void setup(int n) {
                strassenAlgorithm = new StrassenAlgorithm();
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 10, 1);
                m2 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 10, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Matrix result = strassenAlgorithm.multiply(m1, m2);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix multiply with Strassen Algorithm profile", timeable, startN, endMillis);
    }

    public static void profileMatrixMultiplyWithStrassenMultithreading() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            StrassenAlgorithm strassenAlgorithm;
            Matrix m1;
            Matrix m2;

            public void setup(int n) {
                strassenAlgorithm = new StrassenAlgorithm();
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 10, 1);
                m2 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 10, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Matrix result = strassenAlgorithm.multiplyAsyncFuture(m1, m2).get();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix multithreading multiply with Strassen Algorithm profile", timeable, startN, endMillis);
    }

    public static void profileMatrixInvertible() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Matrix m1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    Matrix result = m1.invertible();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix invertible method profile", timeable, startN, endMillis);
    }

    public static void profileLUDecomposition() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Matrix m1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 15, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    LUDecomposition result = new LUDecomposition(m1);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix LU decomposition profile", timeable, startN, endMillis);
    }

    public static void profileQRDecomposition() {
        Timeable timeable = new Timeable() {

            Double intervalEnd;

            Matrix m1;

            public void setup(int n) {
                m1 = HelpfulFunctionsKt.getMatrixWithRandomElementsAndDiagonalDominance(n, 0, 10, 1);

                intervalEnd = (double) n;

                System.out.println("formed intervalEnd = " + intervalEnd);
            }

            public void timeMe(int n) {
                try {
                    QRDecomposition result = new QRDecomposition(m1);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        int startN = 1;
        int endMillis = 1000;

        runProfiler("Matrix LU decomposition profile", timeable, startN, endMillis);
    }

/*
    public static void profileArrayListAddEnd() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                list = new ArrayList<String>();
            }

            public void timeMe(int n) {
                for (int i = 0; i < n; i++) {
                    list.add("a string");
                }
            }
        };
        int startN = 30000;
        int endMillis = 500;
        runProfiler("ArrayList add end", timeable, startN, endMillis);
    }
*/

    /**
     * Runs the profiles and displays results.
     *
     * @param timeable
     * @param startN
     * @param endMillis
     */
    private static void runProfiler(String title, Timeable timeable, int startN, int endMillis) {
        Profiler profiler = new Profiler(title, timeable);
        XYSeries series = profiler.timingLoop(startN, endMillis);
        profiler.plotResults(series);
    }
}
