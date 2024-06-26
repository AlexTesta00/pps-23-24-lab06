package ex2

import ex2.Question.{CONFIDENCE, FINAL, RELEVANCE}

enum Question:
    case RELEVANCE, SIGNIFICANCE, CONFIDENCE, FINAL

trait ConferenceReviewing:
    def loadReview(article: Int, scores: Map[Question, Int]) : Unit
    def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, fin: Int) : Unit
    def orderedScores(article: Int, question: Question): List[Int]
    def averageFinalScore(article: Int): Double
    def acceptedArticles(): Set[Int]
    def sortedAcceptedArticles(): List[(Int, Double)]
    def averageWeightedFinalScoreMap(): Map[Int, Double]

object ConferenceReviewing:
    def apply(): ConferenceReviewing = ConferenceReviewingImpl()

    private case class ConferenceReviewingImpl() extends ConferenceReviewing:
      private var reviews = List[(Int, Map[Question, Int])]()
        override def loadReview(article: Int, scores: Map[Question, Int]): Unit = reviews = reviews.::((article, scores))

        override def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, fin: Int) : Unit =
          reviews = reviews.::((article, Map((Question.RELEVANCE, relevance), (Question.SIGNIFICANCE, significance), (Question.CONFIDENCE, confidence), (Question.FINAL, fin))))

        override def orderedScores(article: Int, question: Question): List[Int] =
          reviews.filter(a => a._1 == article).map(a => a._2(question)).sorted

        override def averageFinalScore(article: Int): Double = averageScore(article, FINAL)

        private def averageScore(article: Int, question: Question): Double = 
          val scores = orderedScores(article, question) ; scores.sum.doubleValue / scores.size

        override def acceptedArticles(): Set[Int] = reviews.filter(a => a._2(Question.RELEVANCE) >= 8 && averageFinalScore(a._1) > 5).map(i => i._1).toSet

        override def sortedAcceptedArticles(): List[(Int, Double)] =
          val acceptedArticles = this.acceptedArticles() ; reviews.filter(a => acceptedArticles.contains(a._1)).map(a => (a._1, averageFinalScore(a._1))).distinct.sortBy(_._2)

        override def averageWeightedFinalScoreMap(): Map[Int, Double] =
          reviews.map(i => (i._1, (averageScore(i._1, CONFIDENCE) * averageFinalScore(i._1)) / 10.0)).toMap