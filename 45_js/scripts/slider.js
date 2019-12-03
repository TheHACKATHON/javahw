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
    s_sliderNext: "#next",
    s_sliderPrev: "#prev",
    s_sliderAutoPlay: "#auto-play",
  },
  state: {
    current: 0,
    count: 3,
    width: 0,
    timer: null,
    interval: 3000,
  },
  init() {
    this.setUpListeners();
    this.getSlidesCount();
    this.getSlideWidth();
  },
  setUpListeners() {
    qs(this.selectors.s_sliderNext).addEventListener('click', () => this.onNext());
    qs(this.selectors.s_sliderPrev).addEventListener('click', () => this.onPrev());
    qs(this.selectors.s_sliderAutoPlay).addEventListener('click', () => this.onAutoPlay());
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
    state.timer = setInterval(() => this.onNext(), +state.interval);
  },
  onStopAutoPlay() {
    const state = this.state;
    clearInterval(state.timer);
    state.timer = null;
  },
  onUpdate() {
    const state = this.state;
    qs('#slide-holder').style.transform = `translateX(-${state.width * state.current}px)`;
  },
  onChangeInterval() {

  },
};
