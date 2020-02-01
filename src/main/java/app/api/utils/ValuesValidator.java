package app.api.utils;


/**
 * Implements helper class that allows to compare measured parameters with reference values.
 * It is done with usage of reference table.
 */
public class ValuesValidator {


    private final double[][][] referenceTable = {

            //PM10
            {
                    {0.0, 21.0},
                    {21.6, 61},
                    {61.1, 101.},
                    {101.1, 141},
                    {141.1, 201.},
                    {201, 10e8}
            },
            //PM 2.5
            {
                    {0., 13.},
                    {13.1, 37.},
                    {37.1, 61.},
                    {61.1, 85.},
                    {85.1, 121},
                    {121, 10e8}
            },
            // O3
            {
                    {0., 71.},
                    {71.1, 121},
                    {121.1, 151.},
                    {151.1, 181.},
                    {181.1, 241},
                    {241, 10e8}
            },
            // NO2
            {
                    {0., 41.},
                    {41.1, 101.},
                    {101.1, 151.},
                    {151., 201.},
                    {201.1, 401},
                    {401, 10e8}
            },
            // SO2
            {
                    {0., 51.},
                    {51.1, 101},
                    {101.1, 201},
                    {201.1, 351.},
                    {351, 501},
                    {501, 10e8},
            },
            // CO
            {
                    {0., 3e3},
                    {3.1e3, 7e3},
                    {7.1e3, 11.e3},
                    {11.1e3, 15.e3},
                    {15.1e3, 21e3},
                    {21e3, 10e8}
            }

    };

    /**
     * Method that compares measured parameter with reference table and sets its status
     * @param keyId parameter id -> table's row id
     * @param meanValue average of measured parameter's values
     * @return status of measurement
     */
    public int validateMeasure(int keyId, double meanValue) {
        int status = -1;
        for (int i = 0; i < referenceTable[keyId].length; i++) {
            if(meanValue > referenceTable[keyId][i][0] && meanValue <= referenceTable[keyId][i][1])
                status = i;
                break;
        }
        return  status;
    }

}
