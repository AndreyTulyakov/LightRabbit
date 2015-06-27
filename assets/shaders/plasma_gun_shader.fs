precision mediump float;
uniform float time;
uniform vec2 resolution;


#define PI 90

void main( void ) {

	vec2 p = ( gl_FragCoord.xy / resolution.xy ) - 0.5;
	
	float sx = 0.3 * (p.x + 0.5) * sin( 900.0 * p.x - 1. * pow(time, 0.666)*5.);
	
	float dy = 4./ ( 500.0 * abs(p.y - sx));
	
	dy += 1./ (100. * length(p - vec2(p.x, 0.)));
	
	vec4 result = vec4( (p.x + 0.1) * dy, 0.3 * dy, dy, 1.0 );
	
	float val = min(result[0],result[1]);
	val = min(val,result[3]);
	result[3] = val;
	
	gl_FragColor = result;
