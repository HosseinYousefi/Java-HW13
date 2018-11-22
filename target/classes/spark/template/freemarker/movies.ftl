<!DOCTYPE html>
<html lang="en">
<head>
    <title>Movie Collection</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

    <meta charset="utf-8">
</head>
<body>
<div class="card-columns">
    <#list movies as movie>
        <div class="card">
            <img class="card-img-top" src="${movie.poster}" alt="${movie.title}" >
            <div class="card-body">
                <h4 class="card-title">${movie.title}</h4>
                <p class="card-text">${movie.director.name}</p>
                <a href="../movie/${movie.id?long?c}" class="btn btn-primary">More info</a>
            </div>
        </div>
    </#list>
</div>
</body>
</html>