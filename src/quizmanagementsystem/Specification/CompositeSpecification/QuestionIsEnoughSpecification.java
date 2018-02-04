package quizmanagementsystem.Specification.CompositeSpecification;

import quizmanagementsystem.Model.Competition;

/**
 *
 * @author anonCoding
 */
public class QuestionIsEnoughSpecification implements CompositeSpecification<Competition> {
   
    @Override
    public boolean isSatisfiedBy(Competition competition) {
        return competition.getQuestions().size() >= (Integer.valueOf(competition.getNoOfRounds()) * Integer.valueOf(competition.getNoOfTeams()));
    }
    
}
