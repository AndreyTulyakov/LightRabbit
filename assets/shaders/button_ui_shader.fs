precision mediump float;


uniform float time; // time
uniform vec2  resolution; // resolution



void main( void ) {

	vec2 p = (gl_FragCoord.yx / resolution.xy) + 2.0 * sin(time);

	
	float dy = 1./ (10. * length(p - vec2(p.x, 0)));
	
	gl_FragColor = vec4(0.0, 0.5 * dy, dy, 1.2);
}