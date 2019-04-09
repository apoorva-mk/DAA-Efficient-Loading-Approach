class GA {

    /* GA parameters */
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), false);

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;
        }

        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            // Select parents
           // Tour parent1 = tournamentSelection(pop);
            //Tour parent2 = tournamentSelection(pop);




              double x = Math.random();

           Tour parent1 = roullet_pal(pop,x);
           Tour parent2 = roullet_pal1(pop,x);
            //Tour parent2 = tournamentSelection(pop);
         // Tour  parent1 = temp_a[0];
           // Tour parent2 = temp_a[1];








            // Crossover parents
            Tour child = crossover(parent1, parent2);
            // Add child to new population
            newPopulation.saveTour(i, child);
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }

        return newPopulation;
    }

    // Applies crossover to a set of parents and creates offspring
    public static Tour crossover(Tour parent1, Tour parent2) {
        // Create new child tour
        Tour child = new Tour();

        // Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent1.tourSize());
        int endPos = (int) (Math.random() * parent1.tourSize());

        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < child.tourSize(); i++) {
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCity(i, parent1.getCity(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.tourSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsCity(parent2.getCity(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    // Mutate a tour using swap mutation
    private static void mutate(Tour tour) {
        // Loop through tour cities
        for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){
            // Apply mutation rate
            if(Math.random() < mutationRate){
                // Get a second random position in the tour
                int tourPos2 = (int) (tour.tourSize() * Math.random());

                // Get the cities at target position in tour
                City city1 = tour.getCity(tourPos1);
                City city2 = tour.getCity(tourPos2);

                // Swap them around
                tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
            }
        }
    }

    // Selects candidate tour for crossover
    private static Tour tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        // Get the fittest tour
        Tour fittest = tournament.getFittest();
        return fittest;
    }


    private static Tour roullet_pal(Population pop,double ran) {
        // Create a tournament population
        int i;
        double rn = ran;
        Population tournament = new Population(pop.populationSize(), false);
        // For each place in the tournament get a random candidate tour and
        // add it
        double[] arr = new double[pop.populationSize()];
        //int i;
        double total_fitness = 0;
        for (i = 0; i < tournament.populationSize(); i++) {
           // int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(i));
        }

        int n = tournament.populationSize();
        for(i=0;i<n;i++)
        {

           
            double a = tournament.getTour(i).getFitness();
            total_fitness += a;
        }
        for(i=0;i<n;i++)
        {
            double a =tournament.getTour(i).getFitness();
            arr[i] = a/total_fitness;
            
        }

        

        double diff = 100000000;
        int index1=0,index2=0;
        for(i=0;i<pop.populationSize();i++)
        {
            if(arr[i]<rn && (rn - arr[i])<diff)
            {
                   index1 = i;
                   diff = rn - arr[i];
            }
            
        }
        return tournament.getTour(index1);



        // Get the fittest tour
       // Tour fittest = tournament.getFittest();
        //return fittest;
    }
    private static Tour roullet_pal1(Population pop,double ran) {
        // Create a tournament population
        int i;
        double rn = ran;
        Population tournament = new Population(pop.populationSize(), false);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (i = 0; i < pop.populationSize(); i++) {
           // int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(i));
        }



         double[] arr = new double[pop.populationSize()];
        //int i;
        double total_fitness = 0;
        

        int n = tournament.populationSize();

        for(i=0;i<n;i++)
        {

            
            double a = pop.getTour(i).getFitness();
            total_fitness += a;
        }

        for(i=0;i<n;i++)
        {
            double a =tournament.getTour(i).getFitness();
            arr[i] = a/total_fitness;
            
        }


         double diff = 100000000;
        int index1=0,index2=0;
        for(i=0;i<pop.populationSize();i++)
        {
            if(arr[i]>rn && (arr[i]-rn)<diff)
            {
                   index1 = i;
                   diff = arr[i] - rn;
            }
            
        }
        return tournament.getTour(index1);



        
    }







/*
     private static Tour[] roullet(Population pop) {
        
        Tour[] temp = new Tour[2];
        double[] arr = new double[pop.populationSize()];
        int i;
        double total_fitness = 0;
        
        int n = pop.populationSize();
        for(i=0;i<n;i++)
        {

            if(pop.getTour(i) == null )
        {
            System.out.println("no popppp"+pop.tours.length);
            return temp;
        }
            double a = pop.getTour(i).getFitness();
            total_fitness += a;
        }
        for(i=0;i<n;i++)
        {
            double a =pop.getTour(i).getFitness();
            arr[i] = a/total_fitness;
            
        }
        double rn = Math.random();
        double diff = 100000000;
        int index1=0,index2=0;
        for(i=0;i<n;i++)
        {
            if(arr[i]<rn && (rn - arr[i])<diff)
            {
                   index1 = i;
                   diff = rn - arr[i];
            }
            
        }
        for(i=0;i<n;i++)
        {
            if(arr[i]>rn && ( arr[i] - rn  )<diff)
            {
                   index2 = i;
                   diff = arr[i]-rn;
            }
            
        }
        
        temp[0] = pop.getTour(index1);
        temp[1] = pop.getTour(index2);
        
        return temp;
        
    }


*/


}
