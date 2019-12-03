const qs = function (selector) {
  return document.querySelector(selector);
};
const qsa = function (selector) {
  return document.querySelectorAll(selector);
};

export const Slider = {
  selectors: {
    s_slider: "#slide-holder",
    s_slide: ".slide",
    s_sliderNext: "#slider-next",
    s_sliderPrev: "#slider-prev",
    s_sliderAutoPlay: "#slider-auto-play",
    s_sliderAutoPlayInterval: "#slider-interval",
  },
  state: {
    current: 0,
    count: 0,
    width: 0,
    timer: null,
    interval: {
      current: 0,
      default: 3000,
      min: 500
    },
  },
  init() {
    this.setUpListeners();
    this.getSlidesCount();
    this.getSlideWidth();
    this.onChangeInterval(this.state.interval.default, true);
  },
  setUpListeners() {
    qs(this.selectors.s_sliderNext).addEventListener('click', () => this.onNext());
    qs(this.selectors.s_sliderPrev).addEventListener('click', () => this.onPrev());
    qs(this.selectors.s_sliderAutoPlay).addEventListener('click', () => this.onAutoPlay());
    qs(this.selectors.s_sliderAutoPlayInterval).addEventListener('change', (e) => {
      const val = e.target.value;
      this.onChangeInterval(val);
    });
    window.addEventListener('resize', () => {
      this.getSlideWidth();
      this.onUpdate();
    });
  },
  getSlidesCount() {
    const sel = this.selectors;
    const slides = qsa(`${sel.s_slider} ${sel.s_slide}`);
    this.state.count = slides.length;
  },
  getSlideWidth() {
    const slide = qs(this.selectors.s_slide);
    if (slide) {
      this.state.width = slide.clientWidth;
    }
  },
  onNext() {
    const state = this.state;
    state.current++;
    if (state.current >= state.count) {
      state.current = 0;
    }
    this.onUpdate();
  },
  onPrev() {
    const state = this.state;
    state.current--;
    if (state.current < 0) {
      state.current = state.count - 1;
    }
    this.onUpdate();
  },
  onAutoPlay() {
    if (this.state.timer) {
      this.onStopAutoPlay();
    } else {
      this.onStartAutoPlay();
    }
  },
  onStartAutoPlay() {
    const state = this.state;
    if (state.timer) {
      this.onStopAutoPlay();
    }
    state.timer = setInterval(() => this.onNext(), state.interval.current);
  },
  onStopAutoPlay() {
    clearInterval(this.state.timer);
    this.state.timer = null;
  },
  onUpdate() {
    qs('#slide-holder').style.transform = `translateX(-${this.state.width * this.state.current}px)`;
  },
  onChangeInterval(val, changeElem) {
    const state = this.state;
    if (val && isFinite(+val) && +val >= state.interval.min) {
      state.interval.current = +val;
      if(changeElem){
        qs(this.selectors.s_sliderAutoPlayInterval).value = +val;
      }
      if (state.timer) {
        this.onStartAutoPlay();
      }
    } else {
      alert("invalid interval, minimum 500");
    }
  },
};
