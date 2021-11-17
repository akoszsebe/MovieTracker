package com.example.movietracker.utils;

public class GenreMapper {

    public static String getImage(String genreName){
        switch (genreName.toUpperCase()){
            case "ACTION" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/hEpWvX6Bp79eLxY1kX5ZZJcme5U.jpg";
            case "ADVENTURE" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/rCzpDGLbOoPwLjy3OAm5NUPOTrC.jpg";
            case "FANTASY" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/sVnMD28upIAlOnZNXT7OOSF5XlR.jpg";
            case "ANIMATION" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg";
            case "DRAMA" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/xq1Ugd62d23K2knRUx6xxuALTZB.jpg";
            case "HORROR" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/2h00HrZs89SL3tXB4nbkiM7BKHs.jpg";
            case "COMEDY" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/f29NQJaWyzXldOGT7F5CDKbwAH9.jpg";
            case "HISTORY" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/4rRxp083IPbH9JX4dyH7ZGXH6ra.jpg";
            case "WESTERN" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/giCRASK5YlIxUMze8NQN3l1icq9.jpg";
            case "THRILLER" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg";
            case "CRIME" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/zkXnKIwX5pYorKJp2fjFSfNyKT0.jpg";
            case "DOCUMENTARY" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/v4QfYZMACODlWul9doN9RxE99ag.jpg";
            case "SCIENCE FICTION" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg";
            case "MYSTERY" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/zumLrxQmbpqhGcjpYcmZ1YXCCvI.jpg";
            case "MUSIC" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg";
            case "ROMANCE" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/u3B2YKUjWABcxXZ6Nm9h10hLUbh.jpg";
            case "FAMILY" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/xnjvwfDulnOCy8qtYX0iqydmMhk.jpg";
            case "WAR" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/miDoEMlYDJhOCvxlzI0wZqBs9Yt.jpg";
            case "TV MOVIE" :
                return "https://image.tmdb.org/t/p/w300_and_h450_bestv2/aSKUFGFHCc1ORK9zQCQMm1prxF1.jpg";
        }
        return "https://s.studiobinder.com/wp-content/uploads/2019/09/Movie-Genres-Types-of-Movies-List-of-Genres-and-Categories-Header-StudioBinder.jpg";
    }
}
