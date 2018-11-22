<!DOCTYPE html>
<html lang="en">
<head>
    <title>${movie.title} - Movie Collection</title>
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
<a href="../../movies" class="btn btn-primary">Back to collection</a>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-4 col-md-5 mt-3">
            <img src="${movie.poster}"/>
        </div>
        <div class="col-lg-8 col-md-7 mt-3">
            <h class="display-3 row">${movie.title}</h>
            <dl class="row col-12">
                <dt class="col-3">Year</dt>
                <dd class="col-9">${movie.year?long?c}</dd>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Released</dt>
                <dd class="col-9">${movie.released}</dd>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Runtime</dt>
                <dd class="col-9">${movie.runtime}</dd>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Genres</dt>
                <div class="col-9">
                    <#list movie.genres as genre>
                        <dd>${genre}</dd>
                    </#list>
                </div>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Director</dt>
                <dd class="col-9">${movie.director.name}</dd>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Writers</dt>
                <div class="col-9">
                    <#list movie.writers as writer>
                        <dd>${writer.name} - <i class="font-weight-light">${writer.type}</i></dd>
                    </#list>
                </div>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Actors</dt>
                <div class="col-9">
                    <#list movie.actors as actor>
                        <dd>${actor.name}     <i class="font-weight-light">as ${actor.as}</i></dd>
                    </#list>
                </div>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Plot</dt>
                <dd class="col-9">${movie.plot}</dd>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Languages</dt>
                <div class="col-9">
                    <#list movie.languages as lang>
                        <dd>${lang}</dd>
                    </#list>
                </div>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Countries</dt>
                <div class="col-9">
                    <#list movie.countries as country>
                        <dd>${country}</dd>
                    </#list>
                </div>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Awards</dt>
                <dd class="col-9">${movie.awards}</dd>
            </dl>

            <dl class="row col-12 mt-3">
                <dt class="col-3">Ratings</dt>
                <div class="col-9">
                    <#list movie.ratings as rating>
                        <dd>${rating.source}: ${rating.value}
                            <#if rating.votes != -1>
                                (${rating.votes} votes)
                            </#if>
                        </dd>
                    </#list>
                </div>
            </dl>
        </div>
    </div>
</div>
</body>
</html>