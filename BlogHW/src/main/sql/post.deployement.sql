-- init data

-- email:    nikita.yevseienko@itstep.org
-- password: superdev
insert into `user` (`email`, `password`, `first_name`, `last_name`)
values
('nikita.yevseienko@itstep.org', '65A8E8E6597979AEBE113FE2834808CD', 'Nikita', 'Yevseienko');

insert into `post` (`title`, `html_body`, `author_id`, `subtitle`, `image_url`)
values
('Fill up your portfolio with meaningful projects in 2020, Use these APIs ',
 '<p>Hi,  You should be filling up your portfolio with new shiny projects, then just landing pages of dummy websites. <br>
People want to see a real-world app with all the data, they want to feel it and they don''t just want lorem ipsum.<br>
So It''s time for you to use an Open API to create a real-world project. You can create a project with these APIs using Vanilla JS, JQuery, VueJS, React or even Angular. <br>
You just need to fetch the data from the APIs and then use it, you can pass different parameters to give the users dynamic search functionality or filter and sorting.</p>

<p>Let''s Start</p>

<h3>
  <a name="tmdb" href="#tmdb" class="anchor">
  </a>
  <a href="https://www.themoviedb.org/">TMDB</a>
</h3>

<p>It''s a very great API to start, who don''t like Movies? There are lots of possibilities, You can create your own IMDB like database website, or better trailer and movie recommendation website with Modern UI. Recruiters really like a project like this, but try to be a little unique cause otherwise, it''ll look like a copy-paste of just API calls.</p>

<h3>
  <a name="open-weather" href="#open-weather" class="anchor">
  </a>
  <a href="https://openweathermap.org/api">Open Weather</a>
</h3>

<p><a href="https://openweathermap.org/api"></a>Weather App is just the simplest example to show, just use this API and pick up a clean UI from dribble or Pinterest and try to make an app, It would be really great if you make a PWA, It will stand out as it''ll show the cached data and can be installed on any phone.</p>

<h3>
  <a name="unsplash-api" href="#unsplash-api" class="anchor">
  </a>
  <a href="https://unsplash.com/developers">Unsplash API</a>
</h3>

<p><a href="https://unsplash.com/developers"></a>I guess you''ve used their website to get backgrounds or stock photos. It''s a huge collection of royalty-free photos. You can also use their API, to get backgrounds and pictures by categories, sizes, search parameters, and random. You can create a chrome extension for a new tab to show a random picture every 1 hour with a clock and Pomodoro.</p>

<h3>
  <a name="pokéapi" href="#pok%C3%A9api" class="anchor">
  </a>
  <a href="https://pokeapi.co/">PokéAPI</a>
</h3>

<p>It''s a really well-cooked API if you''re trying to learn something and it''s really fun, you can create a card game or a Wikipedia of Pokemons. If you love Pokemon then this is the best API for you to get your hands dirty.</p>

<h3>
  <a name="rest-countries-api" href="#rest-countries-api" class="anchor">
  </a>
  <a href="https://restcountries.eu/">Rest Countries API</a>
</h3>

<p><a href="https://restcountries.eu/"></a>It''s the only API you''ll ever need if you''re looking for information about countries. It''ll give you all the information like Currency, Postal Codes, Calling Codes, Capital, Cities, Population.  You can create a beautiful analytical page for every country using charts and data.</p>

<p><a href="https://justaashir.com/blog/free-apis-for-projects/">This article was first published on my official blog</a> </p>',
 1,
 'This article was first published on my official blog',
 'https://dev-to-uploads.s3.amazonaws.com/i/qsr7l5p65i7xx3rwzj5n.jpg'),
('Full Tutorial on how to use SASS to improve your CSS ',
 '<p><div class="fluidvids"><iframe src="https://www.youtube.com/embed/itEFprr8soo" allowfullscreen="" loading="lazy" class=" fluidvids-elem" width="710" height="399">
</iframe></div>
</p>

<p>SASS is pre-processed CSS with tools like nesting, variables, mixins and more. Writing in SASS and then compiling your code to CSS ends up being the same result (CSS) however the pre-processed SASS has more flexibility.</p>

<p>Nesting means you can do things like:</p>

<pre>// CSS is manual, and strict
.css { color:red; }
.css .doesnt { color:darkred; }
.css .doesnt .let .you { color:verydarkred; }
.css .doesnt .let .you { color:veryverydarkred; }
.css .doesnt .let .you .nest { color:veryveryverydarkred; }

// SASS lets you do values and nest!
$color:red;
.sass {
  color:$red;
  .lets {
    color:darken($red,10%);
    .you {
      color:darken($red,20%);
      .nest { color:darken($red,40%);
}}}}
</pre>

<p>If you want to know more, check out the video which will cover:</p>

<ul>
<li>Setting up Sass in Brackets</li>
<li>Difference between CSS and SASS</li>
<li>How nesting works</li>
<li>Using variables</li>
<li>Applying mixins and extends</li>
<li>Using the &amp; operator</li>
</ul>

<p>Check out more about SASS on their website:<br>
<a href="https://sass-lang.com/">https://sass-lang.com/</a></p>

<h2>
  <a name="want-to-see-more" href="#want-to-see-more" class="anchor">
  </a>
  Want to see more:
</h2>

<p>I will try to article new great content every day. Here are the latest items:</p>

<ul>
<li><a href="https://dev.to/adriantwarog/4-simple-css-hover-transitions-for-your-elements-background-4mlg">4 Simple CSS Hover Transitions for your Elements Background</a></li>
<li><a href="https://dev.to/adriantwarog/how-to-implement-dark-mode-with-css-new-media-call-prefers-color-scheme-3h65">How to implement Dark Mode with CSS new media call: prefers-color-scheme</a></li>
<li><a href="https://dev.to/adriantwarog/why-you-should-whiteboard-your-design-development-ui-ux-5g12">Why you should Whiteboard your Design &amp; Development, UI &amp; UX</a></li>
<li><a href="https://dev.to/adriantwarog/creating-a-mobile-design-and-developing-it-5c4o">Creating a Mobile Design and Developing it</a></li>
</ul>

<h3>
  <a name="follow-and-support-me" href="#follow-and-support-me" class="anchor">
  </a>
  Follow and support me:
</h3>

<p><a href="https://www.youtube.com/channel/UCvM5YYWwfLwpcQgbRr68JLQ">Adrian @ Youtube</a><br>
<a href="https://www.patreon.com/adriantwarog">Adrian @ Patreon</a><br>
<a href="https://twitter.com/twarogadrian">Adrian @ Twitter</a></p>

<p>PS. Does anyone know how to add colors to ''pre'' code on dev.to?</p>',
 1,
 'If you use CSS, learning SASS will save heaps of time, code and pain. It''s basically CSS with more features and functionality, and while it''s been around for a while now, it''s good to know and understand it inside and how to take full potential of its abilities.',
 'https://dev-to-uploads.s3.amazonaws.com/i/kupnvrjhlkzebehdhilg.png'
);
